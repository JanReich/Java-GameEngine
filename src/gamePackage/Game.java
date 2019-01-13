package gamePackage;

import engine.abitur.database.DatabaseConnector;
import engine.graphics.Display;

public abstract class Game {

            //Attribute
        private final double VERSION = 1.0;

            //Referenzen
        protected Display display;
        protected DatabaseConnector connector;

    public Game(Display display) {

        this.display = display;
    }

    public Game(Display display, DatabaseConnector connector) {

        this.display = display;
        this.connector = connector;
    }
}
