package no.taardal.blossom.entity.actor;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.entity.Entity;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Sprite;

public class Actor extends Entity {

    private Sprite sprite;

    public Actor(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void update(Keyboard keyboard) {

    }

    @Override
    public void draw(Camera camera) {
        sprite.draw(x, y, camera);
    }

}
