package Engine.Toolbox.uiPackage;

import Engine.Graphics.Display;
import Engine.Graphics.Interfaces.GraphicalObject;
import Engine.Graphics.Interfaces.MouseInterface;
import Engine.Graphics.Interfaces.RemovableObject;
import Engine.Toolbox.Math.Point2f;
import Engine.Toolbox.ResourceHelper.DrawHelper;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TextField implements MouseInterface, RemovableObject {

            //Attribute
        private int width;
        private int height;

            //Referenzen
        private KeyInput input;
        private Display display;
        private Point2f position;
        private String placeholder;
        private Point2f textPosition;

        private Font font;
        private Color fontColor;
        private Color frameColor;

    public TextField(Display display, int x, int y, int width, int height) {

        this.width = width;
        this.height = height;
        this.display = display;
        this.position = new Point2f(x, y);
        this.textPosition = position;

        input = new KeyInput();
        display.getActivePanel().drawObjectOnPanel(input);

        fontColor = Color.BLACK;
        font = new Font("Roboto", Font.BOLD, 18);
        frameColor = Color.darkGray.brighter().brighter().brighter();
    }

    public TextField(Display display, Point2f position, int width, int height) {

        this.width = width;
        this.height = height;
        this.display = display;
        this.position = position;
        this.textPosition = position;

        input = new KeyInput();
        display.getActivePanel().drawObjectOnPanel(input);

        fontColor = Color.BLACK;
        font = new Font("Roboto", Font.BOLD, 18);
        frameColor = Color.darkGray.brighter().brighter().brighter();
    }

    @Override
    public void remove() {

        display.getActivePanel().removeObjectFromPanel(input);
        input = null;
    }

    @Override
    public void draw(DrawHelper draw) {

        draw.setColor(frameColor);
        draw.drawRoundRec((int) position.getX(), (int) position.getY(), width, height, 9);

        draw.setFont(font);
        draw.setColor(fontColor);
        if(draw.getFontWidth(input.getInputQuerry()) < (width - (textPosition.getX() - position.getX()))) {

            if(placeholder != "" && input.getInputQuerry().length() == 0) {

                draw.setColor(fontColor.darker());
                draw.drawString(placeholder, (int) textPosition.getX(), (int) textPosition.getY());
            } else draw.drawString(input.getInputQuerry(), (int) textPosition.getX(), (int) textPosition.getY());
        } else input.removeLetter();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(e.getX() > position.getX() && e.getX()< position.getX() + width && e.getY() > position.getY() && e.getY() < position.getY() + height) {

            input.setTyping(true);
        } else input.setTyping(false);
    }

    //---------- GETTER AND SETTER ----------
    public void setFont(Font font) {

        this.font = font;
    }

    public void setFontColor(Color color) {

        this.fontColor = color;
    }

    public void setFieldInput(String input) {

        this.input.setInputQuerry(input);
    }

    public void setTextPosition(int x, int y) {

        textPosition = new Point2f(x, y);
    }

    public void setFrameColor(Color frameColor) {

        this.frameColor = frameColor;
    }

    public void setPlaceholder(String placeholder) {

        this.placeholder = placeholder;
    }
}
