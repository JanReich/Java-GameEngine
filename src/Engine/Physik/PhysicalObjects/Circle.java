package Engine.Physik.PhysicalObjects;

import Engine.Toolbox.Math.Point2f;

public class Circle extends PhysicalObject {

            //Attribute
        private int radius;

            //Referenzen

    public Circle(int x, int y, int radius) {

        this.x = x;
        this.y = y;
        this.radius = radius;

        this.centerX = x + radius;
        this.centerY = y + radius;
    }

    public Circle(Point2f position, int radius) {

        this.radius = radius;
        this.x = position.getX();
        this.y = position.getY();

        this.centerX = position.getX() + radius;
        this.centerY = position.getY() + radius;
    }

        //---------- GETTER AND SETTER ----------\\
    public int getRadius() {

        return radius;
    }
}
