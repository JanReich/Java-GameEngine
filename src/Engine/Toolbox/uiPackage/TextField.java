package Engine.Toolbox.uiPackage;

import Engine.Graphics.Display;
import Engine.Graphics.Interfaces.GraphicalObject;
import Engine.Graphics.Interfaces.RemovableObject;
import Engine.Toolbox.ResourceHelper.DrawHelper;

import java.awt.*;

public class TextField implements GraphicalObject, RemovableObject {

            //Attribute
        private int width;

            //Referenzen
        private Font font;
        private KeyInput input;
        private Display display;

    public TextField(Display display, int width) {

        this.width = width;
        this.display = display;
        this.input = new KeyInput();
        display.getActivePanel().drawObjectOnPanel(input);
    }

    @Override
    public void draw(DrawHelper draw) {

        draw.setFont(font);
        if(width < draw.getFontWidth(input.getInputQuerry())) input.removeLetter();
    }

    @Override
    public void remove() {

        display.getActivePanel().removeObjectFromPanel(input);
        input = null;
    }

        //---------- GETTER AND SETTER ----------\\
    public KeyInput getInput() {

        return input;
    }

    public void setFont(Font font) {

        this.font = font;
    }

    public void setTyping(boolean typing) {

        input.setTyping(typing);
    }

    public void setInputQuerry(String input) {

        this.input.setInputQuerry(input);
    }
}
