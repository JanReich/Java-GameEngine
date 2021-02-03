package engine.toolbox.math;

public class VectorMath {

  /**
   * In dieser Methode wird ein Winkel zwischen 2 Methoden berechnet
   */
  public static float angle(final Vector2f vec1, final Vector2f vec2) {
    vec1.normalize();
    vec2.normalize();
    return (float) Math.toDegrees(Math.acos(vec1.getPosX() * vec2.getPosX() + vec1.getPosY() * vec2.getPosY()));
  }

  /**
   * Das Skalarprodukt wird berechnet
   */
  public static float dot(final Vector2f vec1, final Vector2f vec2) {
    return ((vec1.getPosX() * vec2.getPosX()) + (vec1.getPosY() * vec2.getPosY()));
  }
}
