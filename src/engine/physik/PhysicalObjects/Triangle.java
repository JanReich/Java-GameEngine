package engine.physik.PhysicalObjects;

import engine.toolbox.math.Point2f;

public class Triangle extends PhysicalObject {

  private Point2f point1;
  private Point2f point2;
  private Point2f point3;

  public Triangle(final Point2f point1, final Point2f point2, final Point2f point3) {
    this.point1 = point1;
    this.point2 = point2;
    this.point3 = point3;
    this.x = point1.getX();
    this.y = point1.getY();
  }

  public Triangle(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
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
