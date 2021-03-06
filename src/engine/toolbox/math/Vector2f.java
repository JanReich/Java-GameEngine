package engine.toolbox.math;

import lombok.Getter;

@Getter
public class Vector2f {

  private float posX;
  private float posY;

  /**
   * Diese Klasse repräsentiert einen Vector im 2D-Raum
   */
  public Vector2f(final Point2f point) {
    this.posX = point.getX();
    this.posY = point.getY();
  }

  /**
   * Diese Klasse repräsentiert einen Vector im 2D-Raum
   */
  public Vector2f(final float posX, final float posY) {
    this.posX = posX;
    this.posY = posY;
  }

  /**
   * Die Länge des Vektors wird berechnet wenn man von jeden Parameter des Vektors quadriert und von diesem dann
   * die Wurzel zieht.
   */
  public float getLength() {
    return (float) Math.sqrt(Math.pow(posX, 2) + Math.pow(posY, 2));
  }

  /**
   * Ein Vektor ist normalisiert, wenn der Betrag (die Länge des Vektors) 1 entspricht!
   * Dieses erreicht man dadurch, dass man den Vector durch seine Länge teilt.
   */
  public void normalize() {
    float length = getLength();
    posX /= length;
    posY /= length;
  }

  /**
   * Die Länge des Vektors wird berechnet wenn man von jeden Parameter des Vektors quadriert und von diesem dann
   * die Wurzel zieht. Den letzten Schritt lassen wir weg und so haben wir die länge zum Quadrat
   */
  public float getLengthSquared() {
    return (float) (Math.pow(posX, 2) + Math.pow(posY, 2));
  }

  // Rechenoperationen mit Vektoren

  /**
   * In dieser Methode wird die Position des Vectors neu bestimmt.
   */
  public void set(final float x, final float y) {
    this.posX = x;
    this.posY = y;
  }

  /**
   * In dieser Methode wird die Position dieses Vectors mit der Position des übergebenen Vectors
   * angeglichen.
   */
  public void set(final Vector2f vec) {
    this.posX = vec.getPosX();
    this.posY = vec.getPosY();
  }

  /**
   * In dieser Methode werden 2 Vectoren addiert.
   */
  public void add(final Vector2f vec) {
    this.add(vec.getPosX(), vec.getPosY());
  }

  /**
   * In dieser Methode werden 2 Vectoren addiert.
   */
  public void add(final float x, final float y) {
    this.posX += x;
    this.posY += y;
  }

  /**
   * In dieser Methode wird von diesem Vektor ein anderer abgezogen
   */
  @Deprecated
  public void sub(final Vector2f vec) {
    this.sub(vec.getPosX(), vec.getPosY());
  }

  /**
   * In dieser Methode wird von diesem Vektor ein anderer abgezogen
   */
  @Deprecated
  public void sub(final float x, final float y) {
    this.posX -= x;
    this.posY -= y;
  }

  /**
   * Der Vektor wird mit -1 multipliziert
   */
  public void negatve() {
    posX *= -1;
    posY *= -1;
  }

  /**
   * Der Vektor wird mit einem Float multipliziert
   */
  public void scale(final float scale) {
    posX *= scale;
    posY *= scale;
  }

  /**
   * Zwischen diesem und dem übergebenem Vektor wird das Skalaprodukt berechnet.
   */
  public float getDotProduct(final Vector2f vec) {
    return (posX * vec.getPosX()) + (posY * vec.getPosY());
  }

  @Override
  public String toString() {
    return "Vector2f {x: " + posX + "|y: " + posY + "}";
  }
}
