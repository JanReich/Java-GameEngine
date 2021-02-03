package engine.networking;

import java.net.Socket;

public class MyClient extends Client {

  public MyClient(final String hostname, final int port) {
    super(hostname, port);
    registerMethod("exampleMessage", new Executable() {
      @Override
      public void run(final Datapackage pack, final Socket socket) {
        //Die Run-Methode wird jedes Mal ausgeführt, wenn der server ein Datenpaket mit der Kennung
        //"exampleMessage" erhält.
      }
    });
  }

  @Override
  public void onConnectionProblem() {
  }

  @Override
  public void onConnectionGood() {
  }

  @Override
  public void onReconnect() {
  }

  @Override
  public void onMessageReceived(final Datapackage pack, final Socket socket) {
  }
}
