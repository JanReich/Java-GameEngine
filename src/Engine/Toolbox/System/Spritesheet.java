package Engine.Toolbox.System;

import Engine.Graphics.Interfaces.GraphicalObject;
import Engine.Logger.MyLogger;
import Engine.Toolbox.ResourceHelper.DrawHelper;
import Engine.Toolbox.ResourceHelper.Image;

public class Spritesheet {

            //Attribute
        private int imagesRow;
        private int imagesCol;

            //Referenzen
        private Image[][] images;
        private Image spritesheet;

    public Spritesheet(String path, int imagesRow, int imagesCol) {

        this(new Image(path), imagesRow, imagesCol);
    }

    public Spritesheet(Image image, int imagesRow, int imagesCol) {

        MyLogger.engineInformation("[Engine] Ein neues Spritesheet wird erstellt...");
        this.imagesRow = imagesRow;
        this.imagesCol = imagesCol;
        this.spritesheet = image;

        this.splitSheet();
    }

    /**
     * Teilt das Spritesheet in kleinere Bilder auf.
     */
    private void splitSheet() {

        if(imagesRow >= 1 && imagesCol >= 1) {

            images = new Image[imagesCol][imagesRow];

            for (int row = 0; row < imagesCol; row++) {
                for (int col = 0; col < imagesRow; col++) {

                    images[row][col] = spritesheet.getSubimage(spritesheet.getWidth() / imagesCol * row, spritesheet.getHeight() / imagesRow * col, spritesheet.getWidth() / imagesCol, spritesheet.getHeight() / imagesRow);
                }
            }

            MyLogger.engineInformation("[Engine] Laden des Spritesheet wurde erfolgreich abgeschlossen!");
        } else MyLogger.warn("[<b>Engine</b>] Ungültige Angaben beim erstellen eines Spritesheets. Row and Col must be great or equal to 1!");
    }

    /**
     * Returnt ein Teil des Spritesheet
     */
    public Image getSpritesheet(int x, int y) {

        if(x < images.length && y < images[x].length)
            return images[x][y];
        else {

            MyLogger.error("[<b>Engine</b>] Ungueltige Auswhahl eines Bildes im Spritesheets. Es existiert kein Bild im Spritesheet mit dem Index: [" + x + "]-[" + y + "]");
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
