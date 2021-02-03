package engine.toolbox.system;

import engine.logger.MyLogger;
import engine.toolbox.resourceHelper.Image;

public class SpriteSheet {

  private final int imagesRow;
  private final int imagesCol;

  private Image[][] images;
  private final Image spriteSheet;

  public SpriteSheet(final String path, final int imagesRow, final int imagesCol) {
    this(new Image(path), imagesRow, imagesCol);
  }

  public SpriteSheet(final Image image, final int imagesRow, final int imagesCol) {
    MyLogger.engineInformation("[engine] Ein neues Spritesheet wird erstellt...");
    this.imagesRow = imagesRow;
    this.imagesCol = imagesCol;
    this.spriteSheet = image;
    this.splitSheet();
  }

  /**
   * Teilt das Spritesheet in kleinere Bilder auf.
   */
  private void splitSheet() {
    if (imagesRow >= 1 && imagesCol >= 1) {
      images = new Image[imagesCol][imagesRow];
      for (int row = 0; row < imagesCol; row++) {
        for (int col = 0; col < imagesRow; col++) {
          images[row][col] = spriteSheet.getSubImage(spriteSheet.getWidth() / imagesCol * row, spriteSheet.getHeight() / imagesRow * col, spriteSheet.getWidth() / imagesCol, spriteSheet.getHeight() / imagesRow);
        }
      }
      MyLogger.engineInformation("[engine] Laden des Spritesheet wurde erfolgreich abgeschlossen!");
    } else {
      MyLogger.warn("[<b>engine</b>] Ungültige Angaben beim erstellen eines Spritesheets. Row and Col must be great or equal to 1!");
    }
  }

  /**
   * Returnt ein Teil des Spritesheet
   */
  public Image getSpritesheet(final int x, final int y) {
    if (x < images.length && y < images[x].length) {
      return images[x][y];
    } else {
      MyLogger.error("[<b>engine</b>] Ungueltige Auswhahl eines Bildes im Spritesheets. Es existiert kein Bild im Spritesheet mit dem Index: [" + x + "]-[" + y + "]");
      return null;
    }
  }

  /**
   * Returnt das Array mit  verschiedenen Subimages
   */
  public Image[][] getImages() {
    return images;
  }
}
