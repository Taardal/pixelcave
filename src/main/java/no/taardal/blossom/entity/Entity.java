package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;

public abstract class Entity {

    protected int x;
    protected int y;
    protected int speed;

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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
