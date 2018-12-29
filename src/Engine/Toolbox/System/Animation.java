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

        private int currentRow;
        private int currentCol;

        private boolean draw;
        private boolean playing;
        private boolean reapeating;

            //Referenzen
        private Point2f position;
        private Spritesheet sheet;
        private Image currentSprite;

    public Animation(String path, int tilesRow, int tilesCol, Point2f position, int width, int height) {

        this(path, tilesRow, tilesCol, (int) position.getX(), (int) position.getY(), width, height);
    }

    public Animation(String path, int tilesRow, int tilesCol, int x, int y, int width, int height) {

        MyLogger.engineInformation("[Engine] Eine neue Animation wird geladen...");

        this.width = width;
        this.height = height;
        this.animationSpeed = 0.04;
        this.position = new Point2f(x, y);

        draw = true;
        currentRow = 0;
        currentCol = 0;
        reapeating = true;
        sheet = new Spritesheet(path, tilesRow, tilesCol);
        currentSprite = sheet.getSpritesheet(0, 0);

        MyLogger.engineInformation("[Engine] Laden der Animation abgeschlossen.");
    }

    @Override
    public void draw(DrawHelper draw) {

        if(this.draw) {

            draw.drawImage(currentSprite, (int) position.getX(), (int) position.getY(), width, height);
        }
    }

    @Override
    public void update(double dt) {

        if(playing)
            timer += dt;

        if(playing) {

            if(timer >= animationSpeed) {

                if(sheet.getImages().length - 1 > currentRow)  {

                    currentRow += 1;
                    currentSprite = sheet.getSpritesheet(currentRow, currentCol);
                } else if(sheet.getImages()[currentRow].length - 1 > currentCol) {

                    currentCol += 1;
                    currentSprite = sheet.getSpritesheet(currentRow, currentCol);
                } else {

                    if(reapeating) {

                        currentRow = 0;
                        currentCol = 0;
                        currentSprite = sheet.getSpritesheet(currentRow, currentCol);
                    } else playing = false;
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
