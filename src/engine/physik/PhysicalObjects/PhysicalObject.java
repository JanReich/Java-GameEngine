package engine.physik.PhysicalObjects;


import engine.toolbox.math.Point2f;

public abstract class PhysicalObject {

  protected double x;
  protected double y;

  protected double centerX;
  protected double centerY;

  public Point2f getPosition() {
    return new Point2f((float) x, (float) y);
  }

  public Point2f getCenterPosition() {
    return new Point2f((float) centerX, (float) centerY);
  }
}