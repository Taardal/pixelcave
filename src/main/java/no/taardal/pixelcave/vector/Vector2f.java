package no.taardal.pixelcave.vector;

public class Vector2f {

    private float x;
    private float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static float getLength(Vector2f a, Vector2f b) {
        float x = Math.abs(a.getX() - b.getX());
        float y = Math.abs(a.getY() - b.getY());
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static Vector2f zero() {
        return new Vector2f(0, 0);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2f copy() {
        return new Vector2f(x, y);
    }

    public Vector2f add(Vector2f vector2f) {
        return new Vector2f(x + vector2f.getX(), y + vector2f.getY());
    }

    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

    public Vector2f subtract(Vector2f vector2f) {
        return new Vector2f(x - vector2f.getX(), y - vector2f.getY());
    }

    public Vector2f subtract(float x, float y) {
        return new Vector2f(this.x - x, this.y - y);
    }

    public Vector2f multiply(float value) {
        return new Vector2f(x * value, y * value);
    }

    public Vector2f divide(float value) {
        return multiply(1 / value);
    }

    public void addAssign(Vector2f vector2f) {
        x += vector2f.getX();
        y += vector2f.getY();
    }

    public void addAssign(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void subtractAssign(Vector2f vector2f) {
        x -= vector2f.getX();
        y -= vector2f.getY();
    }

    public void subtractAssign(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public void multiplyAssign(float value) {
        x *= value;
        y *= value;
    }

    public void divideAssign(float value) {
        multiplyAssign(1 / value);
    }

    public Vector2f minus() {
        return new Vector2f(-x, -y);
    }

    public Vector2f normalize() {
        float length = getLength();
        if (length != 0) {
            float x = this.x / length;
            float y = this.y / length;
            return new Vector2f(x, y);
        } else {
            return Vector2f.zero();
        }
    }

    public float getLength() {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public float getLength(Vector2f vector2f) {
        float x = Math.abs(this.x - vector2f.getX());
        float y = Math.abs(this.y - vector2f.getY());
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public float getDotProduct(Vector2f vector2f) {
        return x * vector2f.x + y * vector2f.y;
    }

    public Vector2f withX(float x) {
        return new Vector2f(x, y);
    }

    public Vector2f withY(float y) {
        return new Vector2f(x, y);
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Vector2f) {
            Vector2f vector2f = (Vector2f) object;
            if (x == vector2f.getX() && y == vector2f.getY()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vector2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


}
