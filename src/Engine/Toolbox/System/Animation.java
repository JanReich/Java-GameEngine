package Engine.Toolbox.System;

import Engine.Graphics.Interfaces.GraphicalObject;
import Engine.Graphics.Interfaces.TimeBasedObject;
import Engine.Logger.MyLogger;
import Engine.Toolbox.Math.Point2f;
import Engine.Toolbox.ResourceHelper.DrawHelper;
import Engine.Toolbox.ResourceHelper.Image;

public class Animation implements GraphicalObject, TimeBasedObject {

            //Attribute
        private int width;
        private int height;
        private double timer;
        private double animationSpeed;

        private int currentCol;
        private int currentRow;

        private boolean draw;
        private boolean playing;
        private boolean reapeating;

        private int playedRow;
        private boolean playPart;
        private boolean correctlyInit;

            //Referenzen
        private Point2f position;
        private Spritesheet sheet;
        private Image currentSprite;

    public Animation(String path, int tilesRow, int tilesCol, int x, int y, int width, int height) {

        this(new Image(path), tilesRow, tilesCol, x, y, width, height);
    }

    public Animation(String path, int tilesRow, int tilesCol, Point2f position, int width, int height) {

        this(path, tilesRow, tilesCol, (int) position.getX(), (int) position.getY(), width, height);
    }

    public Animation(Image image, int tilesRow, int tilesCol, int x, int y, int width, int height) {

        MyLogger.engineInformation("[Engine] Eine neue Animation wird geladen...");

        this.width = width;
        this.height = height;
        this.correctlyInit = true;
        this.animationSpeed = 0.04;
        this.position = new Point2f(x, y);

        draw = true;
        currentCol = 0;
        currentRow = 0;
        reapeating = true;
        sheet = new Spritesheet(image, tilesRow, tilesCol);
        currentSprite = sheet.getSpritesheet(0, 0);

        MyLogger.engineInformation("[Engine] Laden der Animation abgeschlossen.");
    }

    public Animation(String path, int tilesRow, int tilesCol, int x, int y, int width, int height, int playRow) {

        this(new Image(path), tilesRow, tilesCol, x, y, width, height, playRow);
    }

    /**
     * PlayedRow gibt die Reihe an die abgespielt werden soll. Angenommen ein Spritesheet hat 3 Reihen und 5 Spalten und 2 wird als
     * Parameter für playRow angegeben wird die dritte Reihe des Spritesheets abgespielt.
     */
    public Animation(Image image, int tilesRow, int tilesCol, int x, int y, int width, int height, int playRow) {

        if(tilesRow - 1 >= playRow) {

            MyLogger.engineInformation("[Engine] Eine neue Animation wird geladen...");

            this.width = width;
            this.height = height;
            this.animationSpeed = 0.04;
            this.position = new Point2f(x, y);

            this.playPart = true;
            this.playedRow = playRow;
            this.correctlyInit = true;

            draw = true;
            currentCol = 0;
            currentRow = 0;
            reapeating = true;
            sheet = new Spritesheet(image, tilesRow, tilesCol);
            currentSprite = sheet.getSpritesheet(0, 0);

            MyLogger.engineInformation("[Engine] Laden der Animation abgeschlossen.");
        } else MyLogger.warn("[<b>Parameter Fehler</b>] Beim erstellen des Spritesheets ist ein Fehler unterlaufen! Die Reihe die abgespielt werden soll existiert nicht.");

    }

    @Override
    public void draw(DrawHelper draw) {

        if(this.draw && correctlyInit) {

            draw.drawImage(currentSprite, (int) position.getX(), (int) position.getY(), width, height);
        }
    }

    @Override
    public void update(double dt) {

        if(playing && correctlyInit)
            timer += dt;

        if(playing && correctlyInit) {

            if(timer >= animationSpeed) {

                if(playPart) {

                    if (sheet.getImages().length - 1 > currentCol) {

                        currentCol++;
                        currentRow = playedRow;
                        currentSprite = sheet.getSpritesheet(currentCol, currentRow);
                    } else if (reapeating) {

                        currentCol = 0;
                    }
                } else {

                    if(sheet.getImages().length - 1 > currentCol)  {

                        currentCol += 1;
                        currentSprite = sheet.getSpritesheet(currentCol, currentRow);
                    } else if(sheet.getImages()[currentCol].length - 1 > currentRow) {

                        currentRow += 1;
                        currentSprite = sheet.getSpritesheet(currentCol, currentRow);
                    } else {

                        if(reapeating) {

                            currentCol = 0;
                            currentRow = 0;
                            currentSprite = sheet.getSpritesheet(currentCol, currentRow);
                        } else playing = false;
                    }
                }

                timer = 0;
            }
        }
    }

    public void play() {

        playing = true;
    }

    public void stop() {

        playing = false;
    }

        //---------- GETTER AND SETTER ----------
    public void setDraw(boolean draw) {

        this.draw = draw;
    }

    public void setReapeating(boolean reapeating) {

        this.reapeating = reapeating;
    }

    public void setAnimationSpeed(double animationSpeed) {

        this.animationSpeed = animationSpeed;
    }
}
