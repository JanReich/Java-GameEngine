package engine.networking;

import engine.logger.MyLogger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.AlreadyConnectedException;
import java.util.HashMap;
import java.util.UUID;

public abstract class Client {

        protected String id;
        protected String group;

        protected int timeout;
        protected Socket loginSocket;
        protected InetSocketAddress address;

        protected Thread listeningThread;
        protected HashMap<String, Executable> idMethods = new HashMap<>();

        protected int errorCount;
        protected boolean logging;
        protected boolean stopped;

        public static final String CLIENT_LOGIN = "ClientLogin";
        public static final String DEFAULT_GROUP_ID = "NoGroup";
        public static final String DEFAULT_USER_ID = UUID.randomUUID().toString();

    public Client(String hostname, int port) {

        this(hostname, port, 10000, DEFAULT_USER_ID, DEFAULT_GROUP_ID);
    }

    public Client(String hostname, int port, int timeout) {

        this(hostname, port, timeout, DEFAULT_USER_ID, DEFAULT_GROUP_ID);
    }

    /**
     * Im Konstruktor wird ein Client erstellt, der sich mit der übergebenen IP
     * und Port verbinden soll.
     *
     * @param hostname - IP zu der die Verbindung aufgebaut werden soll
     * @param port - Port an dem die Verbindung aufgebaut werden soll
     * @param id - Dient zur Identifizierung des Clients
     */
    public Client(String hostname, int port, String id) {

        this(hostname, port, 10000, id, DEFAULT_GROUP_ID);
    }

    /**
     * Im Konstruktor wird ein Client erstellt, der sich mit der übergebenen IP
     * und Port verbinden soll.
     *
     * @param hostname - IP zu der die Verbindung aufgebaut werden soll
     * @param port - Port an dem die Verbindung aufgebaut werden soll
     * @param id - Dient zur Identifizierung des Clients
     * @param group - Zuweisung zu einer besimmten Gruppe, da Datenpaket
     *              auch an Clients mit einer bestimmten Gruppe gesendet
     *              werden können.
     */
    public Client(String hostname, int port, String id, String group) {

        this(hostname, port, 10000, id, group);
    }

    /**
     * Im Konstruktor wird ein Client erstellt, der sich mit der übergebenen IP
     * und Port verbinden soll.
     *
     * @param hostname - IP zu der die Verbindung aufgebaut werden soll
     * @param port - Port an dem die Verbindung aufgebaut werden soll
     * @param id - Dient zur Identifizierung des Clients
     * @param group - Zuweisung zu einer besimmten Gruppe, da Datenpaket
     *              auch an Clients mit einer bestimmten Gruppe gesendet
     *              werden können.
     */
    public Client(String hostname, int port, int timeout, String id, String group) {

        this.id = id;
        this.group = group;
        this.errorCount = 0;
        this.timeout = timeout;
        this.address = new InetSocketAddress(hostname, port);

        //Standartmäßig wird eine abfrage implementiert, ob die Authentifizierung erfolgreich war
        registerMethod("AuthenticationFailure", new Executable() {

            @Override
            public void run(Datapackage pack, Socket socket) {

                disconnect();
                onError("Die Verbindung zum server wurde getrennt, weil die Authentifizierung fehlgeschlagen ist!");
            }
        });
    }


    //---------------------------------------\\
    //---------- CONNECT AND LOGIN ----------\\
    //---------------------------------------\\

    /**
     * Diese Methode trennt die verbindung zum server
     */
    public void disconnect() {

        stop();
        loginSocket = null;
    }

    /**
     * Diese Methode wird aufgerufen, wenn sich ein neuer Client mit dem server verbindet:
     *
     * Zuerst wird versucht eine Verbindung zum server herzustellen...
     * Authentifizierung beim server, logging in...
     */
    protected void login() {

        if(stopped)
            return;

            // 1. Aufbauen einer Verbindung zum server
        try {
            onLog("Verbindung zum server wird aufgebaut...");
            if (loginSocket != null && loginSocket.isConnected()) {

                onError("Der Client ist bereits mit einem server verbunden...");
                throw new AlreadyConnectedException();
            }

            loginSocket = new Socket();
            loginSocket.connect(this.address, this.timeout);
            onLog("Verbindung zum server hergestellt: " + loginSocket.getRemoteSocketAddress());


                // 2. Authentifizierung beim server, logging in...
            try {

                onLog("Authentifizierung beim server, logging in...");
                ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(loginSocket.getOutputStream()));
                Datapackage loginPackage = new Datapackage(CLIENT_LOGIN, id, group);
                loginPackage.sign(id, group);
                out.writeObject(loginPackage);
                out.flush();
                onLog("Authentifizierungs-Datenpaket wurde erfolgreich zum server gesendet.");
                onReconnect();
            } catch (IOException ex) {

                onError("Authentifizierungs-Datenpaket konnte nicht gesendet werden...");
            }

        } catch(ConnectException e) {

            onError("Client konnte sich nicht mit dem server verbinden: " + e.getMessage());
            onConnectionProblem();
        } catch (IOException e) {

            e.printStackTrace();
            onConnectionProblem();
        }
    }

    /**
     * Überprüft, ob der Client mit dem server verbunden ist und auf eingehende
     * Datenpakete wartet.
     *
     * @return true -> Wenn der Client mit dem server verbunden ist ansonsten -> false
     */
    public boolean isListening() {

        return isConnected() && listeningThread != null && listeningThread.isAlive() && errorCount == 0;
    }

    /**
     * Überprüft, ob der Client mit dem server verbunden ist.
     *
     * @return true wenn der Client mit dem server verbunden ist
     */
    public boolean isConnected() {

        return loginSocket != null && loginSocket.isConnected();
    }

    /**
     * Überprüft, ob der server ereichbar ist
     *
     * @return true -> Wenn der Client den server erreichen kann
     */
    public boolean isServerReachable() {

        try {

            Socket tempSocket = new Socket();
            tempSocket.connect(this.address);
            tempSocket.isConnected();
            tempSocket.close();
            return true;
        } catch(IOException e) {

            return false;
        }
    }

    /**
     * Startet den das empfangen der Datenpakete
     */
    public void start() {

        stopped = false;
        login();
        startListening();
    }

    /**
     * Stoppt das Empfangen der Datenpakete
     */
    public void stop() {

        stopped = true;
        onLog("Der Client wartet nun nicht mehr auf weitere Datenpakete");
    }

    /**
     * Wird aufgerufen, falls der Client die Verbindung verloren hat
     */
    protected void repairConnection() {

        onLog("versuche Verbindung wieder herzustellen...");
        if (loginSocket != null) {

            try {

                loginSocket.close();
            } catch (IOException e) { }
            loginSocket = null;
        }

        login();
        startListening();
    }

    /**
     * Startet einen Thread der auf Datenpakete des Servers wartet.
     */
    protected void startListening() {

        // do not restart the listening thread if it is already running
        if (listeningThread != null && listeningThread.isAlive()) {
            return;
        }

        listeningThread = new Thread(new Runnable() {
            @Override
            public void run() {

                // always repeat if not stopped
                while (!stopped) {
                    try {
                        // repait connection if something went wrong with the connection
                        if (loginSocket != null && !loginSocket.isConnected()) {
                            while (!loginSocket.isConnected()) {
                                repairConnection();
                                if (loginSocket.isConnected())
                                    break;

                                Thread.sleep(5000);
                                repairConnection();
                            }
                        }

                        onConnectionGood();

                            // wait for incoming messages and read them
                        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(loginSocket.getInputStream()));
                        Object raw = ois.readObject();

                        if (stopped)
                            return;

                        if (raw instanceof Datapackage) {
                            final Datapackage msg = (Datapackage) raw;

                            onMessageReceived(msg, loginSocket);

                            // inspect all registered methods
                            for (final String current : idMethods.keySet()) {
                                // if the identifier of a method equals the identifier of the Datapackage...
                                if (current.equalsIgnoreCase(msg.getID())) {
                                    onLog("Datenpaket erhalten. Die Methode mit der Kennung: '" + msg.getID() + "' wird gestartet...");
                                    // execute the registered Executable on a new thread
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {

                                            idMethods.get(current).run(msg, loginSocket);
                                        }
                                    }).start();
                                    break;
                                }
                            }

                        }

                    } catch(SocketException e) {
                        onConnectionProblem();
                        onError("Connection lost");
                        repairConnection();
                    } catch (ClassNotFoundException | IOException | InterruptedException ex) {
                        ex.printStackTrace();
                        onConnectionProblem();
                        onError("Ein Fehler ist aufgetreten: Die Verbindung wurde unterbrochen, der server ist aktuell nicht erreichbar!");
                        repairConnection();
                    }
                    errorCount = 0;
                }
            }
        });

            // start the thread
        listeningThread.start();
    }

    /**
     * Sendet ein Datenpaket an den server
     *
     * @param message - Das gesendete Datenpaket
     * @param timeout - Die Zeit nachdem das Datenpaket nicht mehr gesendet wird
     */
    public Datapackage sendMessage(Datapackage message, int timeout) {

        try {
            // connect to the target client's socket
            Socket tempSocket;

            tempSocket = new Socket();
            tempSocket.connect(address, timeout);

            ObjectOutputStream tempOOS = new ObjectOutputStream(new BufferedOutputStream(tempSocket.getOutputStream()));
            message.sign(id, group);
            tempOOS.writeObject(message);
            tempOOS.flush();
            ObjectInputStream tempOIS = new ObjectInputStream(new BufferedInputStream(tempSocket.getInputStream()));
            Object raw = tempOIS.readObject();

                // close all streams and the socket
            tempOOS.close();
            tempOIS.close();
            tempSocket.close();

                // return the server's reply if it is a Datapackage
            if (raw instanceof Datapackage)
                return (Datapackage) raw;
        } catch(EOFException ex) {
            onError("Fehler beim senden eines Datenpaket: Es wurde keine Antwort (Replay) vom server gesendet!)");
        } catch (IOException | ClassNotFoundException ex) {
            onError("Fehler beim senden eines Datenpaket!");
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Senden eines Datenpaket an den server
     *
     * @param id - Zur Identifizierung
     * @param content - Inhalt des Datenpaket
     * @return Ein Datenpaket, dass vom server gesendet wird
     */
    public Datapackage sendMessage(String id, Object... content) {

        return sendMessage(new Datapackage(id, content));
    }

    /**
     * Senden eines Datenpaket an den server
     *
     * @param message - Das Datenpaket, dass übermittelt wird
     * @return ein Datenpaket, dass vom server übermittelt wird
     */
    public Datapackage sendMessage(Datapackage message) {

        return sendMessage(message, this.timeout);
    }

    /**
     * Registers a method that will be executed if a message containing
     * <i>identifier</i> is received
     *
     * @param identifier
     *            The ID of the message to proccess
     * @param executable
     *            The method to be called when a message with <i>identifier</i> is
     *            received
     */
    public void registerMethod(String identifier, Executable executable) {

        idMethods.put(identifier, executable);
    }

    /**
     * Diese Methode wird aufgerufen, wenn es ein Verbindungsproblem gibt.
     */
    public abstract void onConnectionProblem();

    /**
     * Wenn die Verbindung erfolgreich ist, dann wird diese Methode aufgerufen
     */
    public abstract void onConnectionGood();

    /**
     * Diese Methode wird aufgerufen, wenn ein Reconnect vorgenommen wird.
     */
    public abstract void onReconnect();

    /**
     * Diese Methode wird aufgerufen, wenn eine Nachricht empfangen wird.
     */
    public abstract void onMessageReceived(Datapackage pack, Socket socket);

    /**
     * Dokumentiert wichtige Eigenschaften des Clients in den Logs
     *
     * @param message - Die Nachricht, die in die Logs geschreiben werden soll
     */
    public void onLog(String message) {

        if (logging)
            MyLogger.engineInformation("[<b>Client</b>] " + message);
    }

    /**
     * Dokumentiert die Fehlermeldungen in den logs
     *
     * @param message - Die Fehlermeldung die in den Logs erscheinen soll
     */
    public void onError(String message) {

        if (logging)
            MyLogger.error("[<b>Client</b>] " + message);
    }
}