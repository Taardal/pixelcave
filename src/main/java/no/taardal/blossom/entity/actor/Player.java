package no.taardal.blossom.entity.actor;

import no.taardal.blossom.keyboard.Key;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Sprite;

public class Player extends Actor {

    public Player(Sprite sprite) {
        super(sprite);
        speed = 10;
    }

    @Override
    public void update(Keyboard keyboard) {
        if (keyboard.isPressed(Key.UP) || keyboard.isPressed(Key.W)) {
            y -= speed;
        }
        if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A)) {
            x -= speed;
        }
        if (keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
            x += speed;
        }
        if (keyboard.isPressed(Key.DOWN) || keyboard.isPressed(Key.S)) {
            y += speed;
        }
    }
}
