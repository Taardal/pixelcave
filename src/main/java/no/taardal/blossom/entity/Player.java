package no.taardal.blossom.entity;

import no.taardal.blossom.keyboard.Key;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Sprite;

public class Player extends Actor {

    public Player(Sprite sprite) {
        super(sprite);
        velocityX = 10;
        velocityY = 1;
    }

    @Override
    public void update(Keyboard keyboard) {
        super.update(keyboard);

        if (keyboard.isPressed(Key.UP) || keyboard.isPressed(Key.W)) {
            y -= velocityX;
        }
        if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A)) {
            x -= velocityX;
        }
        if (keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
            x += velocityX;
        }
        if (keyboard.isPressed(Key.DOWN) || keyboard.isPressed(Key.S)) {
            y += velocityX;
        }
    }
}
