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

        MyLogger.engineInformation("[Engine] Ein neues Spritesheet wird erstellt...");
        this.imagesRow = imagesRow;
        this.imagesCol = imagesCol;
        this.spritesheet = new Image(path);

        this.splitSheet();
    }

    /**
     * Teilt das Spritesheet in kleinere Bilder auf.
     */
    private void splitSheet() {

        if(imagesRow >= 1 && imagesCol >= 1) {

            images = new Image[imagesRow][imagesCol];

            for (int row = 0; row < imagesRow; row++) {
                for (int col = 0; col < imagesCol; col++) {

                    images[row][col] = spritesheet.getSubimage(spritesheet.getWidth() / imagesRow * row, spritesheet.getHeight() / imagesCol * col, spritesheet.getWidth() / imagesRow, spritesheet.getHeight() / imagesCol);
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
