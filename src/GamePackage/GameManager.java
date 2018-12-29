package GamePackage;

import Engine.Abitur.Database.DatabaseConnector;
import Engine.Graphics.Display;
import Engine.Graphics.Interfaces.GraphicalObject;
import Engine.Toolbox.ResourceHelper.DrawHelper;
import Engine.Toolbox.ResourceHelper.Image;
import Engine.Toolbox.uiPackage.Button;

public class GameManager implements GraphicalObject {

            //Attribute
        private Image image;

        private Button button;

            //Referenzen
        private Display display;

    public GameManager(Display display) {

        this.display = display;
    }

    public GameManager(Display display, DatabaseConnector connector) {

        this.display = display;


    }

    @Override
    public void draw(DrawHelper draw) {


    }
}
