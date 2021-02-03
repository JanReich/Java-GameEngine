package engine.toolbox.resourceHelper;

import engine.logger.MyLogger;
import engine.physik.PhysicalObjects.Rectangle;
import engine.physik.PhysicalObjects.Triangle;
import engine.toolbox.math.Point2f;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

@Setter
@Getter
public class DrawHelper {

  private int screenWidth;
  private int screenHeight;
  private Graphics2D g2d;

  public DrawHelper(final Graphics2D g2d) {
    this.g2d = g2d;
  }

  //---------- DRAW-STRING ----------
  public void drawString(final String message, final float x, final float y) {
    if (message != null) {
      g2d.drawString(message, x, y);
    } else {
      addLogMessage(1);
    }
  }

  public void drawStringBehind(final String message, final float x, final float y) {
    if (message != null) {
      g2d.drawString(message, (x - getFontWidth(message)), y);
    } else {
      addLogMessage(1);
    }
  }

  public void drawString(final String message, final float x, final float y, final int width) {
    if (message != null) {
      g2d.drawString(message, x + (width - getFontWidth(message)) / 2, y);
    } else {
      addLogMessage(1);
    }
  }

  //---------- Triangle ----------
  public void drawTriangle(final Polygon polygon) {
    g2d.drawPolygon(polygon);
  }

  public void drawTriangle(final Triangle triangle) {
    Polygon polygon = getPolygon(triangle.getPoint1(), triangle.getPoint2(), triangle.getPoint3());
    g2d.drawPolygon(polygon);
  }

  public void drawTriangle(final Point2f point1, final Point2f point2, final Point2f point3) {
    Polygon polygon = getPolygon(point1, point2, point3);
    g2d.drawPolygon(polygon);
  }

  public void fillTriangle(final Polygon polygon) {
    g2d.fillPolygon(polygon);
  }

  public void fillTriangle(final Triangle triangle) {
    Polygon polygon = getPolygon(triangle.getPoint1(), triangle.getPoint2(), triangle.getPoint3());
    g2d.fillPolygon(polygon);
  }

  public void fillTriangle(final Point2f point1, final Point2f point2, final Point2f point3) {
    Polygon polygon = getPolygon(point1, point2, point3);
    g2d.fillPolygon(polygon);
  }

  private Polygon getPolygon(final Point2f point1, final Point2f point2, final Point2f point3) {
    Polygon polygon = new Polygon();
    polygon.addPoint((int) point1.getX(), (int) point1.getY());
    polygon.addPoint((int) point2.getX(), (int) point2.getY());
    polygon.addPoint((int) point3.getX(), (int) point3.getY());
    return polygon;
  }

  //---------- Images ----------
  public void drawImage(final Image image, final float x, final float y) {
    if (image != null) {
      drawImage(image, (int) x, (int) y, image.getWidth(), image.getHeight());
    } else {
      addLogMessage(0);
    }
  }

  public void drawImage(final BufferedImage image, final float x, final float y, final int scale) {
    if (image != null) {
      this.drawImage(image, (int) x, (int) y, scale, scale);
    } else {
      addLogMessage(0);
    }
  }

  public void drawImage(final BufferedImage image, final float x, final float y, final int width, final int height) {
    if (image != null) {
      g2d.drawImage(image, (int) x, (int) y, width, height, null);
    } else {
      addLogMessage(0);
    }
  }

  //---------- Draw-Image (Klasse) ----------
  public void drawImage(final Image image, final int x, final int y, final int scale) {
    if (image != null) {
      this.drawImage(image, x, y, scale, scale);
    } else {
      addLogMessage(0);
    }
  }

  public void drawImage(final Image image, final int x, final int y, final int width, final int height) {
    if (image != null) {
      if (image.getAlpha() != 1) {
        //Set alpha
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, image.getAlpha());
        g2d.setComposite(ac);
        g2d.drawImage(image.getImage(), x, y, width, height, null);
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2d.setComposite(ac);
      } else {
        g2d.drawImage(image.getImage(), x, y, width, height, null);
      }
    } else {
      addLogMessage(0);
    }
  }

  //---------- Draw Line ----------
  public void drawLine(final float x, final float y, final float x2, final float y2) {
    if (g2d != null) {
      g2d.drawLine((int) x, (int) y, (int) x2, (int) y2);
    }
  }

  //---------- DRAW / FILL-Rec ----------
  public void drawRec(final float x, final float y, final int scale) {
    this.drawRec(x, y, scale, scale);
  }

  public void drawRec(final float x, final float y, final int width, final int height) {
    g2d.drawRect((int) x, (int) y, width, height);
  }

  public void drawRec(final Rectangle rectangle) {
    g2d.drawRect((int) rectangle.getPosition().getX(), (int) rectangle.getPosition().getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
  }

  public void fillRec(final float x, final float y, final int scale) {
    this.fillRec(x, y, scale, scale);
  }

  public void fillRec(final float x, final float y, final int width, final int height) {
    g2d.fillRect((int) x, (int) y, width, height);
  }

  public void fillRec(final Rectangle rectangle) {
    g2d.fillRect((int) rectangle.getPosition().getX(), (int) rectangle.getPosition().getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
  }

  //---------- DRAW-RoundRec ----------
  public void drawRoundRec(final float x, final float y, final int scale, final int arc) {
    this.drawRoundRec((int) x, (int) y, scale, scale, arc);
  }

  public void drawRoundRec(final float x, final float y, final int width, final int height, final int arc) {
    g2d.drawRoundRect((int) x, (int) y, width, height, arc, arc);
  }

  public void fillRoundRec(final float x, final float y, final int scale, final int arc) {
    this.fillRoundRec((int) x, (int) y, scale, scale, arc);
  }

  public void fillRoundRec(final float x, final float y, final int width, final int height, final int arc) {
    g2d.fillRoundRect((int) x, (int) y, width, height, arc, arc);
  }

  //---------- Draw Ellipse ----------
  public void drawOval(final float x, final float y, final int scale) {
    this.drawOval(x, y, scale);
  }

  public void drawOval(final float x, final float y, final int width, final int height) {
    if (g2d != null) {
      g2d.drawOval((int) x, (int) y, width, height);
    }
  }

  public void fillOval(final float x, final float y, final int scale) {
    this.fillOval((int) x, (int) y, scale, scale);
  }

  public void fillOval(final float x, final float y, final int width, final int height) {
    if (g2d != null) {
      g2d.fillOval((int) x, (int) y, width, height);
    }
  }

  //---------- Draw HollowCircle ----------
  public void drawHollowCircle(final int centerX, final int centerY, final int radius, final int thickness) {
    if (g2d != null) {
      g2d.draw(getHollowCircle(centerX, centerY, radius, thickness));
    }
  }

  public void fillHollowCircle(final int centerX, final int centerY, final int radius, final int thickness) {
    if (g2d != null) {
      g2d.fill(getHollowCircle(centerX, centerY, radius, thickness));
    }
  }

  private Area getHollowCircle(final int centerX, final int centerY, final int radius, final int thickness) {
    Ellipse2D outer = new Ellipse2D.Double(centerX - radius, centerY - radius, radius * 2, radius * 2);
    Ellipse2D inner = new Ellipse2D.Double(centerX - radius + thickness, centerY - radius + thickness, radius * 2 - thickness * 2, radius * 2 - thickness * 2);
    Area area = new Area(outer);
    area.subtract(new Area(inner));
    return area;
  }

  //---------- Fonts ----------
  public Font createFont(final int fontType, final int fontSize) {
    return new Font("", fontType, fontSize);
  }

  public Font getFont() {
    if (g2d != null) {
      return g2d.getFont();
    }
    return null;
  }

  public void setFont(final Font font) {
    if (g2d != null) {
      g2d.setFont(font);
    }
  }

  public void setFont(final int fontType, final int fontSize) {
    if (g2d != null) {
      g2d.setFont(new Font("", fontType, fontSize));
    }
  }

  public int getFontWidth(final String text) {
    if (g2d != null) {
      return g2d.getFontMetrics().stringWidth(text);
    }
    return -1;
  }

  public int getFontHeight(final String text) {
    if (g2d != null) {
      return g2d.getFontMetrics().getHeight();
    }
    return -1;
  }

  public void setStroke(final int stroke) {
    if (g2d != null) {
      g2d.setStroke(new BasicStroke(stroke));
    }
  }

  //---------- SetColor ----------
  public void setColor(final Color color) {
    if (color != null) {
      g2d.setColor(color);
    } else {
      addLogMessage(2);
    }
  }

  public void setColor(final engine.graphics.Color color) {
    this.setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
  }

  public void setColor(final int r, final int g, final int b) {
    g2d.setColor(new Color(r, g, b));
  }

  public void setColor(final int r, final int g, final int b, final int a) {
    g2d.setColor(new Color(r, g, b, a));
  }

  public void addLogMessage(final int type) {
    if (type == 0) {
      MyLogger.error("[<b>engine-Error</b>] Es wird versucht ein Bild == null zu zeichnen. Überprüfen Sie die Bild datei und den angegebenen Pfad!");
    } else if (type == 1) {
      MyLogger.error("[<b>engine-Error</b>] Es wird versucht ein String == null zu zeichnen. Strings müssen vor dem zeichnen mit \"\" initalisiert werden, falls diese noch keinen Inhalt haben!");
    } else if (type == 2) {
      MyLogger.error("[<b>engine-Error</b>] Es wird versucht mit einer Farbe == null ein Objekt zu zeichnen. Stellen Sie sich sicher, dass Sie die Color-Klasse aus dem Engine-Package benutzen!");
    } else if (type == 3) {
      MyLogger.error("[<b>engine-Error</b>] Es wird versucht mit einer Font == null ein String zu zeichnen.");
    } else {
      MyLogger.error("[<b>engine-Error</b>] Ein bislang unbekannter (in der DrawHelper-Klasse) Fehler ist aufgetreten!");
    }
  }
}
