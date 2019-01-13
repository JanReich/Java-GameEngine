package gamePackage;

import engine.abitur.database.DatabaseConnector;
import engine.graphics.Display;
import engine.graphics.interfaces.GraphicalObject;
import engine.toolbox.resourceHelper.DrawHelper;

    public class GameManager extends Game implements GraphicalObject {

            //Attribute

            //Referenzen

    /**
     * Dieser Konstruktor wird aufgerufen, wenn das laden der Engine abgeschlossen wurde und
     * das Spiel gestartet werden kann.
     *
     * Dieser Konstruktor wird auch nur aufgerufen, wenn MySQL in der Config deaktiviert ist!
     */
    public GameManager(Display display) {

        super(display);
    }

    /**
     * Dieser Konstruktor wird aufgerufen, wenn das laden der Engine abgeschlossen wurde und
     * das Spiel gestartet werden kann.
     *
     * Dieser Konstruktor wird auch nur aufgerufen, wenn MySQL in der Config aktiviert ist!
     */
    public GameManager(Display display, DatabaseConnector connector) {

        super(display, connector);
    }

    @Override
    public void draw(DrawHelper draw) {


    }
}
