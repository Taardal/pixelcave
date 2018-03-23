package no.taardal.pixelcave.bounds;

import no.taardal.pixelcave.vector.Vector2f;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Bounds {

    private Vector2f position;
    private int width;
    private int height;

    public Bounds(Vector2f position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return position.getX();
    }

    public float getRightX() {
        return getX() + getWidth();
    }

    public float getY() {
        return position.getY();
    }

    public float getBottomY() {
        return getY() + getHeight();
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

    public Bounds withPosition(Vector2f position) {
        return new Builder().copy(this).setPosition(position).build();
    }

    public Bounds withWidth(int width) {
        return new Builder().copy(this).setWidth(width).build();
    }

    public Bounds withHeight(int height) {
        return new Builder().copy(this).setHeight(height).build();
    }

    public boolean intersects(Bounds bounds) {
        Rectangle2D rectangle = new Rectangle((int) getX(), (int) getY(), width, height);
        Rectangle2D otherRectangle = new Rectangle((int) bounds.getX(), (int) bounds.getY(), bounds.getWidth(), bounds.getHeight());
        return rectangle.intersects(otherRectangle);
    }

    public static final class Builder {

        private Vector2f position;
        private int width;
        private int height;

        public Builder setPosition(Vector2f position) {
            this.position = position;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder copy(Bounds bounds) {
            position = bounds.position;
            width = bounds.width;
            height = bounds.height;
            return this;
        }

        public Bounds build() {
            return new Bounds(position, width, height);
        }

    }

}
