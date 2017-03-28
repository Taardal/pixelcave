package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;

public abstract class Entity {

    protected int x;
    protected int y;
    protected double velocityX;
    protected double velocityY;

    public abstract void update(Keyboard keyboard);

    public abstract void draw(Camera camera);

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

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}
