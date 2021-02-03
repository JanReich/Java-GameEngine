package engine.physik.PhysicalObjects;

import engine.toolbox.math.Point2f;

public class Circle extends PhysicalObject {

  private final int radius;

  public Circle(final int x, final int y, final int radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.centerX = x + radius;
    this.centerY = y + radius;
  }

  public Circle(final Point2f position, final int radius) {
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
