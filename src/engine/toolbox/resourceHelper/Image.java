package engine.toolbox.resourceHelper;

import engine.graphics.Color;
import engine.logger.MyLogger;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

@Getter
public class Image {

  private int width;
  private int height;
  @Setter
  private float alpha;

  private BufferedImage image;
  private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
      .replace("out/production/GameEngine/", "res/");

  /**
   * Im Konstruktor werden die wichtigsten Attribute initalisiert und das Bild
   * mit dem übergebenen String geladen.
   */
  public Image(final String path) {
    this.alpha = 1f;
    loadImage(path);
  }

  public Image(final BufferedImage image) {
    this.alpha = 1f;
    this.image = image;
    this.width = image.getWidth();
    this.height = image.getHeight();
  }

  /**
   * In dieser Methode wird das Bild, welches diese Klasse repräsentiert
   * geladen.
   */
  private void loadImage(final String path) {
    try {
      image = ImageIO.read(new File(rootPath + path));
      this.width = image.getWidth();
      this.height = image.getHeight();
    } catch (Exception e) {
      MyLogger.error("[engine] Fehler beim laden eines Bildes...");
    }
  }

  public void setPixelColor(final int x, final int y, final Color color) {
    image.setRGB(x, y, color.getRGBA());
  }

  /**
   * Das Bild wird um den degree-Wert rotiert.
   * Aktuell befindet sich in dieser Methode aber ein Bug,
   * beim rotieren kann es sein, dass das Bild teilweise
   * abgeschnitten wird.
   */
  public void rotate(final int degree) {
    MyLogger.warn("[<b>engine-Bug</b>] Achtung bei der Rotation eines Images kommt es in der aktuellen engine-Version "
        + "noch zu einem Fehler. Teile des rotierten Images könnten abgeschnitten sein");
    double radians = Math.toRadians(degree);
    AffineTransform tx = new AffineTransform();
    tx.rotate(radians, image.getWidth() / 2, image.getHeight() / 2);

    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    image = op.filter(image, null);
  }

  public int getPixelColor(final int x, final int y, final Color pixelOut) {
    return image.getRGB(x, y);
  }

  public Image getSubImage(int x, int y, int width, int height) {
    return new Image(image.getSubimage(x, y, width, height));
  }
}
