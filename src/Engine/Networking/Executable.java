package Engine.Networking;

import java.net.Socket;

public interface Executable {

    /**
     * @param pack - Das Datenpaket
     * @param socket - Die Verbindung von der das Datenpaket empfangen wird
     */
    void run(Datapackage pack, Socket socket);
}
