package gamePackage;

import engine.abitur.database.DatabaseConnector;
import engine.graphics.Display;
import engine.graphics.interfaces.GraphicalObject;

import engine.toolbox.resourceHelper.DrawHelper;

public class GameManager implements GraphicalObject {

            //Attribute

            //Referenzen
        private Display display;


    /**
     * Dieser Konstruktor wird aufgerufen, wenn in der Config MySQL deaktiviert ist.
     */
    public GameManager(Display display) {

        this.display = display;
    }

    /**
     * Dieser Konstruktor wird aufgerufen, wenn in der Config MySQL aktiviert ist.
     */
    public GameManager(Display display, DatabaseConnector connector) {

        this.display = display;


        Layout layout = new Layout(display);
        display.getActivePanel().drawObjectOnPanel(layout);
    }

    @Override
    public void draw(DrawHelper draw) {

    }
}
