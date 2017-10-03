package no.taardal.pixelcave.vector;

public class Vector2d {

    private double x;
    private double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double getLength(Vector2d a, Vector2d b) {
        double x = Math.abs(a.getX() - b.getX());
        double y = Math.abs(a.getY() - b.getY());
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static Vector2d zero() {
        return new Vector2d(0, 0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector2d copy() {
        return new Vector2d(x, y);
    }

    public Vector2d add(Vector2d vector2i) {
        return new Vector2d(x + vector2i.getX(), y + vector2i.getY());
    }

    public Vector2d add(double x, double y) {
        return new Vector2d(this.x + x, this.y + y);
    }

    public Vector2d subtract(Vector2d vector2i) {
        return new Vector2d(x - vector2i.getX(), y - vector2i.getY());
    }

    public Vector2d subtract(double x, double y) {
        return new Vector2d(this.x - x, this.y - y);
    }

    public Vector2d multiply(double value) {
        return new Vector2d(x * value, y * value);
    }

    public Vector2d divide(double value) {
        return multiply(1 / value);
    }

    public void addAssign(Vector2d vector2i) {
        x += vector2i.getX();
        y += vector2i.getY();
    }

    public void addAssign(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void subtractAssign(Vector2d vector2i) {
        x -= vector2i.getX();
        y -= vector2i.getY();
    }

    public void subtractAssign(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    public void multiplyAssign(double value) {
        x *= value;
        y *= value;
    }

    public void divideAssign(double value) {
        multiplyAssign(1 / value);
    }

    public Vector2d minus() {
        return new Vector2d(-x, -y);
    }

    public Vector2d normalize() {
        double length = getLength();
        if (length != 0) {
            double x = this.x / length;
            double y = this.y / length;
            return new Vector2d(x, y);
        } else {
            return Vector2d.zero();
        }
    }

    public double getLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getDotProduct(Vector2d vector2i) {
        return x * vector2i.x + y * vector2i.y;
    }

    @Override
    public String toString() {
        return "Vector2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Vector2d) {
            Vector2d vector2I = (Vector2d) obj;
            if (x == vector2I.getX() && y == vector2I.getY()) {
                return true;
            }
        }
        return false;
    }


}
