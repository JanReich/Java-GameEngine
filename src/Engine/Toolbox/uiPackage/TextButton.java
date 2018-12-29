package Engine.Toolbox.uiPackage;

import Engine.Graphics.Interfaces.MouseInterface;
import Engine.Toolbox.Math.Point2f;
import Engine.Toolbox.ResourceHelper.DrawHelper;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TextButton implements MouseInterface {

            //Attribute
        private int width;
        private int height;

            //Referenzen
        private String text;
        private Point2f position;

        private Font textFont;
        private Color textColor;
        private Color bodyColor;
        private Color frameColor;

    public TextButton(String text, int x, int y, int width, int height) {

        this(text, new Point2f(x, y), width, height);
    }

    public TextButton(String text, Point2f position, int width, int height) {

        this.text = text;
        this.width = width;
        this.height = height;
        this.position = position;

        this.bodyColor = Color.WHITE;
        this.textColor = Color.BLACK;
        this.frameColor = Color.BLACK;
        this.textFont = new Font("Roboto", Font.BOLD, 18);
    }

    @Override
    public void draw(DrawHelper draw) {

        draw.setColor(bodyColor);
        draw.fillRoundRec((int) position.getX(), (int) position.getY(), width, height, 9);
        draw.setColor(frameColor);
        draw.drawRoundRec((int) position.getX(), (int) position.getY(), width, height, 9);

        draw.setFont(textFont);
        draw.setColor(textColor);
        draw.drawString(text, (int) (position.getX()  + (width - draw.getFontWidth(text)) / 2), (int) (position.getY() + (height / 4) * 3));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

        //---------- GETTER AND SETTER ----------
    public void setTextFont(Font textFont) {

        this.textFont = textFont;
    }

    public void setBodyColor(Color bodyColor) {

        this.bodyColor = bodyColor;
    }

    public void setTextColor(Color textColor) {

        this.textColor = textColor;
    }

    public void setFrameColor(Color frameColor) {

        this.frameColor = frameColor;
    }
}
