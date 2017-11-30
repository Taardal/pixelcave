package no.taardal.pixelcave.bounds;

import no.taardal.pixelcave.vector.Vector2f;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Bounds {

    private Vector2f position;
    private int width;
    private int height;

    public Bounds() {
        position = Vector2f.zero();
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean intersects(Bounds bounds) {
        Rectangle2D rectangle = new Rectangle((int) getX(), (int) getY(), width, height);
        Rectangle2D otherRectangle = new Rectangle((int) bounds.getX(), (int) bounds.getY(), bounds.getWidth(), bounds.getHeight());
        return rectangle.intersects(otherRectangle);
    }

}
