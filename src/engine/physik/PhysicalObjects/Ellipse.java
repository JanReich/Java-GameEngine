package engine.physik.PhysicalObjects;

import engine.toolbox.math.Point2f;

public class Ellipse extends PhysicalObject {

  private final int width;
  private final int height;

  public Ellipse(final int x, final int y, final int width, final int height) {
    this(new Point2f(x, y), width, height);
  }

  public Ellipse(final Point2f position, final int width, final int height) {
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
