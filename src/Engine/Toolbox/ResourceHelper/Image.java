package Engine.Toolbox.ResourceHelper;

import Engine.Logger.MyLogger;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class Image {

            //Attribute
        private int width;
        private int height;
        private float alpha;

            //Referenzen
        private BufferedImage image;

        private String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("out/production/GameEngine/", "res/");


    /**
     * Im Konstruktor werden die wichtigsten Attribute initalisiert und das Bild
     * mit dem übergebenen String geladen.
     */
    public Image(String path) {

        this.alpha = 1f;

        loadImage(path);
    }

    public Image(BufferedImage image) {

        this.alpha = 1f;
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
     * In dieser Methode wird das Bild, welches diese Klasse repräsentiert
     * geladen.
     */
    private void  loadImage(String path) {

        try {

            image = ImageIO.read(new File(rootPath + path));
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (Exception e) {

            MyLogger.error("[Engine] Fehler beim laden eines Bildes...");
        }
    }

    public void rotate(int degree) {

        MyLogger.warn("[<b>Engine-Bug</b>] Achtung bei der Rotation eines Images kommt es in der aktuellen Engine-Version noch zu einem Fehler. Teile des rotierten Images koennten abgeschnitten sein");
        double radians = Math.toRadians(degree);
        AffineTransform tx = new AffineTransform();
        tx.rotate(radians, image.getWidth() / 2, image.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        image = op.filter(image, null);
    }

        // ---------- GETTER AND SETTER ----------
    public int getWidth() {

            return width;
        }

    public int getHeight() {

        return height;
    }


    public float getAlpha() {

        return alpha;
    }

    public BufferedImage getImage() {

        return image;
    }

    public void setAlpha(float alpha) {

        this.alpha = alpha;
    }

    public void setImage(BufferedImage image) {

        this.image = image;
    }

    public Image getSubimage(int x, int y, int width, int height) {

        return new Image(image.getSubimage(x, y, width, height));
    }
}
