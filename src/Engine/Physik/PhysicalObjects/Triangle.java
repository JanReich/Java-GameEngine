package Engine.Physik.PhysicalObjects;

import Engine.Toolbox.Math.Point2f;

public class Triangle extends PhysicalObject {

            //Attribute

            //Referenzen
        private Point2f point1;
        private Point2f point2;
        private Point2f point3;

    public Triangle(Point2f point1, Point2f point2, Point2f point3) {

        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;

        this.x = point1.getX();
        this.y = point1.getY();
    }

    public Triangle(float x1, float y1, float x2, float y2, float x3, float y3) {

        this.point1 = new Point2f(x1, y1);
        this.point2 = new Point2f(x2, y2);
        this.point3 = new Point2f(x3, y3);

        this.x = point1.getX();
        this.y = point1.getY();
    }

        //---------- GETTER AND SETTER ----------\\
    public Point2f getPoint1() {

        return point1;
    }

    public Point2f getPoint2() {

        return point2;
    }

    public Point2f getPoint3() {

        return point3;
    }
}
