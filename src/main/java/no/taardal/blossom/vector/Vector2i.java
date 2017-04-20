package no.taardal.blossom.vector;

public class Vector2i {

    private int x;
    private int y;

    public Vector2i() {
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(Vector2i vector2i) {
        this.x = vector2i.getX();
        this.y = vector2i.getY();
    }

    public static double getDistance(Vector2i vector2i, Vector2i vector2i1) {
        double x = vector2i.getX() - vector2i1.getX();
        double y = vector2i.getY() - vector2i1.getY();
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Vector2i) {
            Vector2i vector2i = (Vector2i) obj;
            if (getX() == vector2i.getX() && getY() == vector2i.getY()) {
                return true;
            }
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void add(Vector2i vector2i) {
        this.x += vector2i.getX();
        this.y += vector2i.getY();
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void add(int value) {
        this.x += value;
        this.y += value;
    }

    public void subtract(Vector2i vector2i) {
        this.x -= vector2i.getX();
        this.y -= vector2i.getY();
    }

    public void subtract(int x, int y) {
        this.x -= x;
        this.y -= y;
    }

    public void subtract(int value) {
        this.x -= value;
        this.y -= value;
    }

}
