package no.taardal.blossom.bounds;

import java.awt.*;

public class Bounds {

    private Rectangle rectangle;

    public Bounds() {
        rectangle = new Rectangle();
    }

    public int getWidth() {
        return rectangle.width;
    }

    public void setWidth(int width) {
        this.rectangle.width = width;
    }

    public int getHeight() {
        return rectangle.height;
    }

    public void setHeight(int height) {
        this.rectangle.height = height;
    }

    public int getX() {
        return rectangle.x;
    }

    public void setX(int x) {
        this.rectangle.x = x;
    }

    public int getY() {
        return rectangle.y;
    }

    public void setY(int y) {
        this.rectangle.y = y;
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public boolean intersects(Bounds bounds) {
        return rectangle.intersects(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

}
