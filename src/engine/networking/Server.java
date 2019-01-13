package engine.networking;

import engine.logger.MyLogger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class Server {

    protected ServerSocket server;
    protected Thread listeningThread;
    protected ArrayList<RemoteClient> clients;
    protected ArrayList<RemoteClient> toBeDeleted;
    protected HashMap<String, Executable> idMethods = new HashMap<>();

    protected int port;
    protected boolean logging;
    protected boolean stopped;
    protected boolean autoRegisterEveryClient;

    protected int minPlayer;
    protected int maxPlayer;

    protected long pingInterval = 30*1000; // 30 seconds
    protected static final String INTERNAL_LOGIN_ID = "ClientLogin";

    /**
     * IM Konstruktor wird ein neuer server am dem angegebenen Prot erstellt.
     * Jeder Client der sich mit dem server verbindet wird registiert und kann
     * Nachrichten empfangen. <br>
     * Im Angegebenen Intervall wird ein Ping an jeden Client versandt damit die
     * Verbindung aufrecht bleibt.
     * @param port - Der Port an dem der server erstellt wird.
     * @param logging - Ob die Einzelheiten des Servers in den Logs gespeichert werden sollen
     */
    public Server(int port, boolean logging) {

        this(port, true, true, logging);
    }

    /**
     * Ein server wird auf dem angegebenen Port erstellt.
     *
     * @param port - Der Port auf dem der Serve erstellt werden soll
     * @param autoRegister - Dieser Parameter bestimmt, ob jeder Client der sich mit dem server
     *                    verbindet automatisch gespeichert werden soll.
     * @param keepConnectionAlive - Dieser Parameter bestimmt, ob in einem bestimmten Intervall
     *                            ein Ping-Signal gesendet werden soll. Das Intervall kann mit
     *                            <code>setPingInterval(int seconds)</code> geändert werden.
     * @param logging - Ob die Einzelheiten des Servers in den Logs gespeichert werden sollen
     */
    public Server(int port, boolean autoRegister, boolean keepConnectionAlive, boolean logging) {

        this(port, autoRegister, keepConnectionAlive, logging, -1, -1);
    }

    public Server(int port, boolean autoRegister, boolean keepConnectionAlive, boolean logging, int minPlayer, int maxPlayer) {

        if(logging) {

            MyLogger.info("[<b>server</b>] Ein neuer Server auf dem Port: " + port + " wurde gestartet.");
            MyLogger.info("[<b>server</b>] Der Server besitzt folgende Configuration: Autoregister: " + autoRegister + ", Pinging: " + keepConnectionAlive);
        }

        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;

        this.port = port;
        this.logging = logging;
        this.clients = new ArrayList<>();
        this.autoRegisterEveryClient = autoRegister;

        if (autoRegisterEveryClient)
            registerLoginMethod();

        registerDatapackageIDs();
        start();

        if (keepConnectionAlive)
            startPingThread();
    }

    //------------------------------------------------------------\\
    //---------- Auswertung der ankommenden Datenpakete ----------\\
    //------------------------------------------------------------\\

    /**
     * In diesem Thread werden die ankommenden Datenpaket ausgewertet
     *
     *
     */
    protected void startListening() {

        if (listeningThread == null && server != null) {

            listeningThread = new Thread(new Runnable() {

                @Override
                public void run() {

                    while (!Thread.interrupted() && !stopped && server != null) {

                        try {

                            onLog("Der server wartet auf eingehende Verbindungen...");
                            final Socket tempSocket = server.accept();

                                //Eingehende Datenpakete auslesen
                            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(tempSocket.getInputStream()));
                            Object raw = ois.readObject();

                            if (raw instanceof Datapackage) {

                                final Datapackage msg = (Datapackage) raw;
                                onLog("Datenpaket erhalten: " + msg);
                                onMessageReceived(msg, tempSocket);
                                for (final String current : idMethods.keySet()) {

                                    if (msg.getID().equalsIgnoreCase(current)) {

                                        onLog("ausführen der Executable-Methode mit der Kennung: '" + msg.getID() + "'");
                                        new Thread(new Runnable() {

                                            @Override
                                            public void run() {

                                                idMethods.get(current).run(msg, tempSocket);
                                                if (!msg.getID().equals(INTERNAL_LOGIN_ID)) {

                                                    try {

                                                        tempSocket.close();
                                                    } catch (IOException e) {

                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }).start();
                                        break;
                                    }
                                }
                            }
                        } catch (SocketException e) {

                            onLog("server wurde gestoppt.");
                            onServerStopped();
                        } catch (IllegalBlockingModeException | IOException | ClassNotFoundException e) {

                            e.printStackTrace();
                        }
                    }
                }
            });
            listeningThread.start();
        }
    }

    /**
     * Diese Methode ist für die automatisch Registrierung verantwortlich.
     * Dazu wird in dieser Methode ein Handler erstellt, der alle Client automatisch
     * registiert.
     */
    protected void registerLoginMethod() {

        idMethods.put(INTERNAL_LOGIN_ID, new Executable() {

            @Override
            public void run(Datapackage msg, Socket socket) {

                if((minPlayer == -1 && maxPlayer == -1) && (maxPlayer < clients.size())) {

                    if (msg.size() == 3)
                        registerClient((String) msg.get(1), (String) msg.get(2), socket);
                    else if (msg.size() == 2)
                        registerClient((String) msg.get(1), socket);
                    else
                        registerClient(UUID.randomUUID().toString(), socket);
                    onClientRegistered(msg, socket);
                } else {

                    RemoteClient remoteClient;
                    if (msg.size() == 3)
                        remoteClient = new RemoteClient((String) msg.get(1), (String) msg.get(2), socket);
                    else if (msg.size() == 2)
                        remoteClient = new RemoteClient((String) msg.get(1), socket);
                    else
                        remoteClient = new RemoteClient(UUID.randomUUID().toString(), socket);

                    Datapackage datapackage = new Datapackage("AuthenticationFailure", "server Full");
                    sendMessage(remoteClient, datapackage);

                    onError("Es sind bereits zu viele Clients mit dem server verbunden! Verbindung wird wieder getrennt...");
                }
            }
        });
    }


    /**
     * Ändert das Intervall indem ein Ping vom server an alle Clients
     * gesendet wird
     *
     * @param seconds - Der Parameter bestimmt das Intervall, indem
     */
    public void setPingInterval(int seconds) {

        this.pingInterval = seconds * 1000;
    }

    /**
     * Das Anpingen von Clients wird in einem bestimmten Intervall durchgeführt,
     * alle Clients werden angepint um zu überprüfen, ob die Verbindung noch
     * inordnung ist
     */
    protected void startPingThread() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                while (server != null) {

                    try {

                        Thread.sleep(pingInterval);
                    } catch (InterruptedException e) { }
                    broadcastMessage(new Datapackage("_INTERNAL_PING_", "OK"));
                }

            }
        }).start();
    }

    /**
     * Sendet ein (Replay) antwort Datenpaket zum Client. Diese Methode darf
     * nur innerhalb einer run-Method eines Executable objekt implementiert werden.
     *
     * @param toSocket - Die Verbindung zwischen Client und server
     * @param datapackageContent - Der Inhalt des Datenpaket, dass gesendet werden soll
     */
    public synchronized void sendReply(Socket toSocket, Object... datapackageContent) {

        sendMessage(new RemoteClient(null, toSocket), new Datapackage("REPLY", datapackageContent));
    }

    /**
     * Sendet eine Nachricht an einen Client mit einer speziellen ClientID
     *
     * @param remoteClientId - Die ID des Client, der die Nachricht empfangen soll
     * @param datapackageId - Die ID des Datenpaket, das versendet werden soll
     * @param datapackageContent - Der Inhalt des Datenpaket
     */
    public synchronized void sendMessage(String remoteClientId, String datapackageId, Object... datapackageContent) {

        sendMessage(remoteClientId, new Datapackage(datapackageId, datapackageContent));
    }

    /**
     * Sendet eine Nachricht an einen Client mit einer speziellen ClientID
     *
     * @param remoteClientId - Die ID an den das Datenpaket gesendet werden soll
     * @param message - Das Datenpaket, dass gesendet werden soll
     */
    public synchronized void sendMessage(String remoteClientId, Datapackage message) {

        for (RemoteClient current : clients)
            if (current.getId().equals(remoteClientId))
                sendMessage(current, message);
    }

    /**
     * Sendet eine Nachricht an einen Client mit einer speziellen ClientID
     *
     * @param remoteClient - Der Client der die Nachricht empfangen soll
     * @param datapackageId - Die ID des Datenpakets
     * @param datapackageContent - Inhalt des Datenpakets
     */
    public synchronized void sendMessage(RemoteClient remoteClient, String datapackageId, Object... datapackageContent) {

        sendMessage(remoteClient, new Datapackage(datapackageId, datapackageContent));
    }

    /**
     * Sendet eine Nachricht an einen Client mit einer speziellen ClientID
     *
     * @param remoteClient - Der Client, an den die Nachricht gesendet werden soll.
     * @param message - Das Datenpaket, dass dem Client gesendet wird
     */
    public synchronized void sendMessage(RemoteClient remoteClient, Datapackage message) {

        try {
                // send message
            if (!remoteClient.getSocket().isConnected()) {

                onError("Es wird versucht, eine Nachricht an einen Client zu senden, der nicht mit dem server verbunden ist!");
                throw new ConnectException("Socket not connected.");
            }

            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(remoteClient.getSocket().getOutputStream()));
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            onError("Fehler beim senden einer Nachricht an einen Client: " + e.getMessage());

            if (toBeDeleted != null)
                toBeDeleted.add(remoteClient);
            else {
                clients.remove(remoteClient);
                onClientRemoved(remoteClient);
            }
        }
    }

    /**
     * Sendet ein Datenpaket an eine Gruppe von Clients
     *
     * @param group - Den Namen der Gruppe an die die Nachricht gesendet wird.
     *              Der Name der Gruppe zu der ein Client zugehörig ist wird beim
     *              Login festgelegt.
     * @param message - Das Datenpaket, dass an die Gruppe übermittelt wird.
     * @return die Anzahl der Client an die die Nachricht geschrieben wird.
     */
    public synchronized int broadcastMessageToGroup(String group, Datapackage message) {

        toBeDeleted = new ArrayList<RemoteClient>();

            //Sende das Datenpaket
        int txCounter = 0;
        for (RemoteClient current : clients) {
            if (current.getGroup().equals(group)) {
                sendMessage(current, message);
                txCounter++;
            }
        }

            //Verbindung von clients trennen, die Fehler verursachen.
        txCounter -= toBeDeleted.size();
        for (RemoteClient current : toBeDeleted) {
            clients.remove(current);
            onClientRemoved(current);
        }

        toBeDeleted = null;
        return txCounter;
    }

    /**
     * Sendet ein Datenpaket an alle Clients
     *
     * @param message - Das Datenpaket, dass versendet werden soll
     * @return Die Anzahl der Clients, die das Datenpaket erhalten
     */
    public synchronized int broadcastMessage(Datapackage message) {

        toBeDeleted = new ArrayList<>();


            //Sende das Datenpaket
        int txCounter = 0;
        for (RemoteClient current : clients) {

            sendMessage(current, message);
            txCounter++;
        }

            //Verbindung von clients trennen, die Fehler verursachen.
        txCounter -= toBeDeleted.size();
        for (RemoteClient current : toBeDeleted) {
            clients.remove(current);
            onClientRemoved(current);
        }

        toBeDeleted = null;

        return txCounter;
    }

    /**
     * Diese Methode registiert eine Methode, die ausgeführt wird, falls in einem Datenpaket
     * die entsprechende Kennung (identifier) gefunden wird.
     *
     * @param identifier - Die ID (Kennung) des Datenpaket
     * @param executable - Die Methode wird aufgerufen wenn einer Registierte Kennung
     *                   in einem Datenpaket gefunden wird.
     */
    public void registerMethod(String identifier, Executable executable) {

        if (identifier.equalsIgnoreCase(INTERNAL_LOGIN_ID) && autoRegisterEveryClient) {

            onError("Die Kennung darf nicht:  '" + INTERNAL_LOGIN_ID + "' sein! Clients werden automatisch registriert, sie müssen nicht manuel registiert werden");
            throw new IllegalArgumentException("Die Kennung darf nicht:  '" + INTERNAL_LOGIN_ID + "' sein! Clients werden automatisch registriert, sie müssen nicht manuel registiert werden");
        }
        idMethods.put(identifier, executable);
    }

    /**
     * Einen Client beim server registieren, damit dieser Nachrichten empfangen kann.
     *
     * @param id - Die ID des Client
     * @param newClientSocket - Die Verbindung des Client mit dem server
     */
    protected synchronized void registerClient(String id, Socket newClientSocket) {

        clients.add(new RemoteClient(id, newClientSocket));
    }

    /**
     * Einen Client beim server registieren, damit dieser Nachrichten empfangen kann.
     *
     * @param id - Die ID des Clients
     * @param group - Name der Clientgruppe
     * @param newClientSocket - Die aktive Verbindung zwischen Client und server
     */
    protected synchronized void registerClient(String id, String group, Socket newClientSocket) {

        clients.add(new RemoteClient(id, group, newClientSocket));
    }

    /**
     * Diese Methode startet den server automatisch, nachdem die verschiedenen Kennungen
     * beim server registiert wurden.
     */
    protected void start() {

        stopped = false;
        server = null;
        try {

            server = new ServerSocket(port);
        } catch (IOException e) {

            onError("Ein Fehler beim starten des Servers ist aufgetreten!");
            onError("Fehlermeldung: " + e.getMessage());
            e.printStackTrace();
        }
        startListening();
    }

    /**
     * Dieser Methode stoppt den server
     */
    public void stop() {

        try {

            stopped = true;
            if (listeningThread.isAlive())
                listeningThread.interrupt();

            if (server != null)
                server.close();
        } catch (IOException e) {

            onError("Fehler beim schließen des Servers");
        }
    }

    /**
     * Diese Methode zählt die Anzahl der beim server registierten Clients
     *
     * @return Die Anzahl der verbundenen Clients
     */
    public synchronized int getClientCount() {

        return clients != null ? clients.size() : 0;
    }

    /**
     * Diese Methode überprüft, ob ein Client mit einer bestimmten ID
     * aktuell mit dem server verbunden ist.
     *
     * @param clientId - Die ID des Clients
     * @return true wenn ein RemoteClient mit der ClientID mit dem server verbunden ist
     */
    public boolean isClientIdConnected(String clientId) {

        if(clients != null && clients.size() > 0) {

            for(RemoteClient c : clients) {

                if(c.getId().equals(clientId) && c.getSocket() != null && c.getSocket().isConnected()) return true;
            }
        }
        return false;
    }

    /**
     * Diese Methode überprüft, ob ein Client mit dem server verbunden ist
     * @return true == ClientAmount >= 1 || false == ClientAmount == 0
     */
    public boolean isAnyClientConnected() {

        return getClientCount() > 0;
    }

    /**
     * Diese Methode wird aufgerufen, bevor der server gestartet wird. In dieser
     * Methode sollen alle Events registriert werden, die abgefragt werden.
     *
     * Wenn ein Client ein Datenpaket an den server schickt befindet sich im Index 0
     * die Kennung die beim server registiert werden muss.
     *
     * <code>registerMethod(String identifier (datapackageID), Executable executable)</code>!
     */
    public abstract void registerDatapackageIDs();

    /**
     * Diese Methode wird aufgerufen, wenn der server gestoppt wird.
     */
    public abstract void onServerStopped();

    /**
     * Diese Methode wird aufgerufen, wenn ein Client die Verbindung zum server trennt
     * oder der Client gekickt wird.
     *
     * @param remoteClient - Der Client, der die Verbindung zum server geschlossen hat
     */
    public abstract void onClientRemoved(RemoteClient remoteClient);

    /**
     * Diese Methode wird aufgerufen, wenn sich ein neuer Client beim server
     * registriert.
     *
     * @param msg - Das Datenpacket mit dem sich der Client registiert
     * @param socket - Die Verbindung zwischen server und Client.
     */
    public abstract void onClientRegistered(Datapackage msg, Socket socket);

    /**
     * Diese Methode wird aufgerufen, wenn der server eine Nachricht empfängt.
     *
     * @param msg - Das Datenpaket, dass der server empfangen hat
     * @param socket - Die Verbindung, von der das Datenpaket gesendet wurde
     */
    public abstract void onMessageReceived(Datapackage msg, Socket socket);

    /**
     * Diese Methode dient zur Protokollierung verschiedener Funktionen des Servers
     * in den Logs.
     *
     * @param message - Die Nachricht, die in den Logs steht
     */
    public void onLog(String message) {

        if (logging) MyLogger.info("[<b>server</b>] " + message);
    }

    /**
     * Diese Methode wird jedes Mal aufgerufen, wenn ein Fehler auftritt,
     * dieser wird dann in den Logs festgehalten.
     *
     * @param message - Die Fehlermeldung, die in den Logs steht
     */
    public void onError(String message) {

        if (logging) MyLogger.error("[<b>server-Error</b>] " + message);
    }


    protected class RemoteClient {

                //Attribute

                //Referenzen
            private String id;
            private String group;
            private Socket socket;

        /**
         * In diesem Konstruktor wird ein RemoteClient erstellt, der einen Client repräsentiert
         * der server mit dem server connected ist.
         *
         * @param id - Die ID des Clients
         * @param socket - Die Verbindung mit dem Client
         */
        public RemoteClient(String id, Socket socket) {

            this.id = id;
            this.socket = socket;
            this.group = "Default Client";
        }

        /**
         * In diesem Konstruktor wird ein RemoteClient erstellt, der einen Client repräsentiert
         * der server mit dem server connected ist.
         *
         * @param id - Die ID des Clients
         * @param group - Sendergruppe des Clients
         * @param socket - Die Verbindung mit dem Client
         */
        public RemoteClient(String id, String group, Socket socket) {

            this.id = id;
            this.group = group;
            this.socket = socket;
        }

        public String getId() {

            return id;
        }

        public String getGroup() {

            return group;
        }

        public Socket getSocket() {

            return socket;
        }

        /**
         * Returnt einen String der einen RemoteClient repräsentiert im Format:
         * <i>[RemoteClient ID (GROUP) @ SOCKET_REMOTE_ADDRESS]</i>
         */
        @Override
        public String toString() {

            return "[RemoteClient: " + id + " (" + group + ") @ " + socket.getRemoteSocketAddress() + "]";
        }
    }
}
