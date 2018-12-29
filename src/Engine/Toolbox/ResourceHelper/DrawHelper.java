package Engine.Toolbox.ResourceHelper;

import Engine.Logger.MyLogger;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawHelper {

            //Attribute
        private int screenWidth;
        private int screenHeight;

            //Referenzen
        private Graphics2D g2d;

    public DrawHelper(Graphics2D g2d) {

        this.g2d = g2d;
    }

        //---------- DRAW-STRING ----------

    public void drawString(String message, int x, int y) {

        if(message != null)
            g2d.drawString(message, x, y);
        else addLogMessage(1);
    }

    public void drawString(String message, int x, int y, int width) {

        if(message != null)
            g2d.drawString(message, x + (width - getFontWidth(message)) / 2, y);
        else addLogMessage(1);
    }

        //---------- DRAW-Image ----------

    public void drawImage(Image image, int x, int y) {

        if(image != null)
            drawImage(image, x, y, image.getWidth(), image.getHeight());
        else addLogMessage(0);
    }

    public void drawImage(BufferedImage image, int x, int y, int scale) {

        if(image != null)
            this.drawImage(image, x, y, scale, scale);
        else addLogMessage(0);
    }

    public void drawImage(BufferedImage image, int x, int y, int width, int height) {

        if(image != null)
            g2d.drawImage(image, x, y, width, height, null);
        else addLogMessage(0);
    }

        //---------- Draw-Image (Klasse) ----------

    public void drawImage(Image image, int x, int y, int width, int height) {

        if(image != null) {

            if(image.getAlpha() != 1) {

                    //Set alpha
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, image.getAlpha());
                g2d.setComposite(ac);

                g2d.drawImage(image.getImage(), x, y, width, height, null);

                ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
                g2d.setComposite(ac);
            } else {

                g2d.drawImage(image.getImage(), x, y, width, height, null);
            }
        } else addLogMessage(0);
    }

        //---------- DRAW-RoundRec ----------

    public void drawRoundRec(int x, int y, int width, int height, int arc) {

        g2d.drawRoundRect(x, y, width, height, arc, arc);
    }

    public void fillRoundRec(int x, int y, int width, int height, int arc) {

        g2d.fillRoundRect(x, y, width, height, arc, arc);
    }

        //---------- SetColor ----------

    public void setColor(Color color) {

        if(color != null)
            g2d.setColor(color);
        else addLogMessage(2);
    }

    public void setColor(int r, int g, int b, int a) {

        g2d.setColor(new Color(r, g, b, a));
    }

        //---------- Set Font ----------

    public void setFont(Font font) {

        if(font != null)
            g2d.setFont(font);
        else addLogMessage(3);
    }

        //---------- Get Font Width ----------
    public int getFontWidth(String text) {

        return g2d.getFontMetrics().stringWidth(text);
    }
        //---------- GETTER AND SETTER ----------

    public Graphics2D getG2d() {

        return g2d;
    }

    public int getScreenWidth() {

        return screenWidth;
    }

    public int getScreenHeight() {

        return screenHeight;
    }

    public void updateGraphics(Graphics2D g2d) {

        this.g2d = g2d;
    }

    public void setScreenWidth(int screenWidth) {

        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {

        this.screenHeight = screenHeight;
    }

    public void addLogMessage(int type) {

        if(type == 0)
            MyLogger.error("[<b>Engine-Error</b>] Es wird versucht ein Bild == null zu zeichnen.");
        else if(type == 1)
            MyLogger.error("[<b>Engine-Error</b>] Es wird versucht ein String == null zu zeichnen.");
        else if(type == 2)
            MyLogger.error("[<b>Engine-Error</b>] Es wird versucht mit einer Farbe == null ein Objekt zu zeichnen.");
        else if(type == 3)
            MyLogger.error("[<b>Engine-Error</b>] Es wird versucht mit einer Font == null ein String zu zeichnen.");
        else
            MyLogger.error("[<b>Engine-Error</b>] Ein bislang unbekannter (in der DrawHelper-Klasse) Fehler ist aufgetreten!");
    }
}