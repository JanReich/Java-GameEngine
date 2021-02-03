package engine.physik.PhysicalObjects;

import engine.toolbox.math.Point2f;

public class Rectangle extends PhysicalObject {

  private double width;
  private double height;

  public Rectangle(final double x, final double y, final int width, final int height) {
    this(new Point2f((float) x, (float) y), width, height);
  }

  public Rectangle(final Point2f position, final int width, final int height) {
    this.width = width;
    this.height = height;
    this.x = position.getX();
    this.y = position.getY();
    if (x < 0) {
      centerX = ((x - width) / 2) * -1;
    } else {
      centerX = (x + width) / 2;
    }
    if (y < 0) {
      centerY = ((y - height) / 2) * -1;
    } else {
      centerY = (y + height) / 2;
    }
  }

  /**
   * Wenn X und Y innerhalb des Rechtecks ist wird true returnt ansonsten false
   */
  public boolean isInside(final double x, final double y) {
    return isInside(new Point2f((float) x, (float) y));
  }

  /**
   * Wenn der Punkt innerhalb des Rechtecks ist wird true returnt ansonsten false
   */
  public boolean isInside(final Point2f point) {
    if (point.getX() > x && point.getX() < x + width && point.getY() > y && point.getY() < y + height) {
      return true;
    } else {
      return false;
    }
  }

  public void move(final double dx, final double dy) {
    this.x += dx;
    this.y += dy;
  }

  public void setPosition(final Point2f point) {
    this.x = point.getX();
    this.y = point.getY();
  }

  public void setPosition(final double x, final double y) {
    this.x = x;
    this.y = y;
  }

  //---------- GETTER AND SETTER ----------\\
  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }
}
