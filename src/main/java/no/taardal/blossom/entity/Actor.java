package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Sprite;

import java.awt.*;

public class Actor extends Entity {

    protected Sprite sprite;
    protected Rectangle boundingBox;
    protected boolean falling;

    public Actor(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    @Override
    public void update(Keyboard keyboard) {
        if (falling) {
            y += velocityY;
        }
    }

    @Override
    public void draw(Camera camera) {
        sprite.draw(x, y, camera);
        camera.drawRectangle(x, y, getWidth(), getHeight(), Color.RED);
    }
}
