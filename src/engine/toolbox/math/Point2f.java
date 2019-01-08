package engine.toolbox.math;

public class Point2f {

            //Attribute
        private float x;
        private float y;

            //Referenzen

    /**
     * Diese Klasse repräsentiert einen Punkt im 2D-Koordinatensystem
     */
    public Point2f(Point2f point) {

        this(point.getX(), point.getY());
    }

    /**
     * Diese Klasse repräsentiert einen Punkt im 2D-Koordinatensystem
     */
    public Point2f(float x, float y) {

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
    public void setLocation(Point2f point) {

        this.setLocation(point.getX(), point.getY());
    }

    /**
     * Überschreibt den Punkt
     */
    public void setLocation(float x, float y) {

        this.x = x;
        this.y = y;
    }

    /**
     * Überschreibt den Punkt
     */
    public void translate(float dx, float dy) {

        x += dx;
        y += dy;
    }

    /**
     *  Gibt den aktuellen Punkt als Vektor wieder.
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
