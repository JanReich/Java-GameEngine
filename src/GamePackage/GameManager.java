package GamePackage;

import Engine.Abitur.Database.DatabaseConnector;
import Engine.Graphics.Display;
import Engine.Graphics.Interfaces.GraphicalObject;

import Engine.Toolbox.ResourceHelper.DrawHelper;

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


    }

    @Override
    public void draw(DrawHelper draw) {

    }
}
