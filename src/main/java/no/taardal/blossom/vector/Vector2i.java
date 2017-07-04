package no.taardal.blossom.vector;

public class Vector2i {

    private int x;
    private int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static double getLength(Vector2i a, Vector2i b) {
        int x = a.getX() - b.getX();
        int y = a.getY() - b.getY();
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static Vector2i zero() {
        return new Vector2i(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector2i copy() {
        return new Vector2i(x, y);
    }

    public Vector2i add(Vector2i vector2i) {
        return new Vector2i(x + vector2i.getX(), y + vector2i.getY());
    }

    public Vector2i subtract(Vector2i vector2i) {
        return new Vector2i(x - vector2i.getX(), y - vector2i.getY());
    }

    public Vector2i multiply(int value) {
        return new Vector2i(x * value, y * value);
    }

    public Vector2i divide(int value) {
        return multiply(1 / value);
    }

    public Vector2i multiply(double value) {
        return new Vector2i((int) (x * value), (int) (y * value));
    }

    public Vector2i divide(double value) {
        return multiply(1 / value);
    }

    public Vector2i minus() {
        return new Vector2i(-x, -y);
    }

    public Vector2i normalize() {
        double length = getLength();
        if (length != 0) {
            int x = (int) (this.x / length);
            int y = (int) (this.y / length);
            return new Vector2i(x, y);
        } else {
            return Vector2i.zero();
        }
    }

    public double getLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getDotProduct(Vector2i vector2i) {
        return x * vector2i.x + y * vector2i.y;
    }

    @Override
    public String toString() {
        return "Vector2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Vector2i) {
            Vector2i vector2I = (Vector2i) obj;
            if (x == vector2I.getX() && y == vector2I.getY()) {
                return true;
            }
        }
        return false;
    }

    public void addEq(Vector2i vector2i) {
        x += vector2i.getX();
        y += vector2i.getY();
    }

    public void subtractEq(Vector2i vector2i) {
        x -= vector2i.getX();
        y -= vector2i.getY();
    }

    public void multiplyEq(int value) {
        x *= value;
        y *= value;
    }

    public void divideEq(int value) {
         multiplyEq(1 / value);
    }

}
