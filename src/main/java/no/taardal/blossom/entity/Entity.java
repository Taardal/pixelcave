package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;

public abstract class Entity {

    protected int x;
    protected int y;
    protected int velocityX;
    protected int velocityY;

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

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
}
