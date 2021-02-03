package engine.toolbox.math;

public class Point2f {

  private float x;
  private float y;

  /**
   * Diese Klasse repräsentiert einen Punkt im 2D-Koordinatensystem
   */
  public Point2f(final Point2f point) {
    this(point.getX(), point.getY());
  }

  /**
   * Diese Klasse repräsentiert einen Punkt im 2D-Koordinatensystem
   */
  public Point2f(final float x, final float y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Aktuelle Location als Point
   */
  public Point2f getLocation() {
    return new Point2f(x, y);
  }

  /**
   * Überschreibt den Punkt
   */
  public void setLocation(final Point2f point) {
    this.setLocation(point.getX(), point.getY());
  }

  /**
   * Überschreibt den Punkt
   */
  public void setLocation(final float x, final float y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Überschreibt den Punkt
   */
  public void translate(final float dx, final float dy) {
    x += dx;
    y += dy;
  }

  /**
   * Gibt den aktuellen Punkt als Vektor wieder.
   */
  public Vector2f toVector() {
    return new Vector2f(x, y);
  }

  //---------- GETTER AND SETTER ----------
  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public String toString() {
    return "Point2f {x: " + x + "|y: " + y + "}";
  }
}
