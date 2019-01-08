package engine.networking;

import java.net.Socket;

public class MyClient extends Client {

    public MyClient(String hostname, int port) {

        super(hostname, port);

        registerMethod("exampleMessage", new Executable() {

            @Override
            public void run(Datapackage pack, Socket socket) {

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
    public void onMessageReceived(Datapackage pack, Socket socket) {

    }
}
