package engine.toolbox.math;

    public class VectorMath {

        /**
         * In dieser Methode wird ein Winkel zwischen 2 Methoden berechnet
         */
        public static float angle(Vector2f vec1, Vector2f vec2) {

            vec1.normalize();
            vec2.normalize();

            return (float) Math.toDegrees(Math.acos(vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY()));
        }

        /**
         * Das Skalarprodukt wird berechnet
         */
        public static float dot(Vector2f vec1, Vector2f vec2) {

            return ((vec1.getX() * vec2.getX()) + (vec1.getY() * vec2.getY()));
        }
    }
