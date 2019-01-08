package Engine.Toolbox.ResourceHelper;

import Engine.Logger.MyLogger;
import Engine.Physik.PhysicalObjects.Rectangle;
import Engine.Physik.PhysicalObjects.Triangle;
import Engine.Toolbox.Math.Point2f;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
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

    public void drawString(String message, float x, float y) {

        if(message != null)
            g2d.drawString(message, x, y);
        else addLogMessage(1);
    }

    public void drawStringBehind(String message, float x, float y) {

        if(message != null)
            g2d.drawString(message, (x - getFontWidth(message)), y);
        else addLogMessage(1);
    }

    public void drawString(String message, float x, float y, int width) {

        if(message != null)
            g2d.drawString(message, x + (width - getFontWidth(message)) / 2, y);
        else addLogMessage(1);
    }

        //---------- Triangle ----------

    public void drawTriangle(Polygon polygon) {

        g2d.drawPolygon(polygon);
    }

    public void drawTriangle(Triangle triangle) {

        Polygon polygon = getPolygon(triangle.getPoint1(), triangle.getPoint2(), triangle.getPoint3());
        g2d.drawPolygon(polygon);
    }

    public void drawTriangle(Point2f point1, Point2f point2, Point2f point3) {

        Polygon polygon = getPolygon(point1, point2, point3);
        g2d.drawPolygon(polygon);
    }

    public void fillTriangle(Polygon polygon) {


        g2d.fillPolygon(polygon);
    }

    public void fillTriangle(Triangle triangle) {

        Polygon polygon = getPolygon(triangle.getPoint1(), triangle.getPoint2(), triangle.getPoint3());
        g2d.fillPolygon(polygon);
    }

    public void fillTriangle(Point2f point1, Point2f point2, Point2f point3) {

        Polygon polygon = getPolygon(point1, point2, point3);

        g2d.fillPolygon(polygon);
    }

    private Polygon getPolygon(Point2f point1, Point2f point2, Point2f point3) {

        Polygon polygon = new Polygon();
        polygon.addPoint((int) point1.getX(), (int) point1.getY());
        polygon.addPoint((int) point2.getX(), (int) point2.getY());
        polygon.addPoint((int) point3.getX(), (int) point3.getY());
        return polygon;
    }

        //---------- Images ----------

    public void drawImage(Image image, float x, float y) {

        if(image != null)
            drawImage(image, (int) x, (int) y, image.getWidth(), image.getHeight());
        else addLogMessage(0);
    }

    public void drawImage(BufferedImage image, float x, float y, int scale) {

        if(image != null)
            this.drawImage(image, (int) x, (int) y, scale, scale);
        else addLogMessage(0);
    }

    public void drawImage(BufferedImage image, float x, float y, int width, int height) {

        if(image != null)
            g2d.drawImage(image, (int) x, (int) y, width, height, null);
        else addLogMessage(0);
    }

        //---------- Draw-Image (Klasse) ----------

    public void drawImage(Image image, int x, int y, int scale) {

        if(image != null)
            this.drawImage(image, x, y, scale, scale);
        else addLogMessage(0);
    }

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

        //---------- Draw Line ----------
    public void drawLine(float x, float y, float x2, float y2) {

        if(g2d != null) g2d.drawLine((int) x, (int) y, (int) x2, (int) y2);
    }

        //---------- DRAW / FILL-Rec ----------

    public void drawRec(float x, float y, int scale) {

        this.drawRec(x, y, scale, scale);
    }

    public void drawRec(float x, float y, int width, int height) {

        g2d.drawRect((int) x, (int) y, width, height);
    }

    public void drawRec(Rectangle rectangle) {

        g2d.drawRect((int) rectangle.getPosition().getX(), (int) rectangle.getPosition().getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    public void fillRec(float x, float y, int scale) {

        this.fillRec(x, y, scale, scale);
    }

    public void fillRec(float x, float y, int width, int height) {

        g2d.fillRect((int) x, (int) y, width, height);
    }

    public void fillRec(Rectangle rectangle) {

        g2d.fillRect((int) rectangle.getPosition().getX(), (int) rectangle.getPosition().getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

        //---------- DRAW-RoundRec ----------

    public void drawRoundRec(float x, float y, int scale, int arc) {

        this.drawRoundRec((int) x, (int) y, scale, scale, arc);
    }

    public void drawRoundRec(float x, float y, int width, int height, int arc) {

        g2d.drawRoundRect((int) x, (int) y, width, height, arc, arc);
    }

    public void fillRoundRec(float x, float y, int scale, int arc) {

        this.fillRoundRec((int) x, (int) y, scale, scale, arc);
    }

    public void fillRoundRec(float x, float y, int width, int height, int arc) {

        g2d.fillRoundRect((int) x, (int) y, width, height, arc, arc);
    }

        //---------- Draw Ellipse ----------
    public void drawOval(float x, float y, int scale) {

        this.drawOval(x, y, scale);
    }

    public void drawOval(float x, float y, int width, int height) {

        if(g2d != null) g2d.drawOval((int) x, (int) y, width, height);
    }

    public void fillOval(float x, float y, int scale) {

        this.fillOval((int) x, (int) y, scale, scale);
    }

    public void fillOval(float x, float y, int width, int height) {

        if(g2d != null) g2d.fillOval((int) x, (int) y, width, height);
    }

        //---------- Draw HollowCircle ----------
    public void drawHollowCircle(int centerX, int centerY, int radius, int thickness) {

        if(g2d != null) {

            Ellipse2D outer = new Ellipse2D.Double(centerX- radius, centerY - radius, radius * 2, radius * 2);
            Ellipse2D inner = new Ellipse2D.Double(centerX - radius + thickness, centerY - radius + thickness, radius * 2 - thickness * 2, radius * 2 - thickness * 2);

            Area area = new Area(outer);
            area.subtract(new Area(inner));

            g2d.draw(area);
        }
    }

    public void fillHollowCircle(int centerX, int centerY, int radius, int thickness) {

        if(g2d != null) {

            Ellipse2D outer = new Ellipse2D.Double(centerX- radius, centerY - radius, radius * 2, radius * 2);
            Ellipse2D inner = new Ellipse2D.Double(centerX - radius + thickness, centerY - radius + thickness, radius * 2 - thickness * 2, radius * 2 - thickness * 2);

            Area area = new Area(outer);
            area.subtract(new Area(inner));

            g2d.fill(area);
        }
    }

        //---------- Fonts ----------
    public Font createFont(int fontType, int fontSize) {

        return new Font("", fontType, fontSize);
    }

    public Font getFont() {

        if(g2d != null) return g2d.getFont();
        return null;
    }

    public void setFont(Font font) {

        if(g2d != null) g2d.setFont(font);
    }

    public void setFont(int fontType, int fontSize) {

        if(g2d != null) g2d.setFont(new Font("", fontType, fontSize));
    }

    public int getFontWidth(String text) {

        if(g2d != null) return g2d.getFontMetrics().stringWidth(text);
        return -1;
    }

    public int getFontHeight(String text) {

        if(g2d != null) return g2d.getFontMetrics().getHeight();
        return -1;
    }

    public void setStroke(int stroke) {

        if(g2d != null) g2d.setStroke(new BasicStroke(stroke));
    }

        //---------- SetColor ----------

    public void setColor(Color color) {

        if(color != null)
            g2d.setColor(color);
        else addLogMessage(2);
    }

    public void setColor(Engine.Graphics.Color color) {

        this.setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public void setColor(int r, int g, int b) {

        g2d.setColor(new Color(r, g, b));
    }

    public void setColor(int r, int g, int b, int a) {

        g2d.setColor(new Color(r, g, b, a));
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
