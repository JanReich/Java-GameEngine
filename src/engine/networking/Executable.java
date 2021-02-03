package engine.networking;

import java.net.Socket;

public interface Executable {

  /**
   * @param pack   - Das Datenpaket
   * @param socket - Die Verbindung von der das Datenpaket empfangen wird
   */
  void run(final Datapackage pack, final Socket socket);
}
