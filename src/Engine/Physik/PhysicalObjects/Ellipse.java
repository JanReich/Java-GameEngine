package Engine.Physik.PhysicalObjects;

import Engine.Toolbox.Math.Point2f;

public class Ellipse extends PhysicalObject {

            //Attribute
        private int width;
        private int height;

            //Referenzen

    public Ellipse(int x, int y, int width, int height) {

        this(new Point2f(x, y), width, height);
    }

    public Ellipse(Point2f position, int width, int height) {

        this.width = width;
        this.height = height;
        this.x = position.getX();
        this.y = position.getY();

        centerX = position.getX() + width;
        centerY = position.getY() + height;
    }

        //---------- GETTER AND SETTER ----------\\
    public int getWidth() {

        return width;
    }

    public int getHeight() {

        return height;
    }
}
