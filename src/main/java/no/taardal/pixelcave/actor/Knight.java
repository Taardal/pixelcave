package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.state.actor.knight.KnightAttackingMidAirState;
import no.taardal.pixelcave.state.actor.knight.KnightAttackingState;
import no.taardal.pixelcave.state.actor.knight.KnightFallingState;
import no.taardal.pixelcave.vector.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Knight extends Actor implements Player {

    private static final Logger LOGGER = LoggerFactory.getLogger(Knight.class);

    public enum Theme {

        BLACK,
        BLUE,
        ELDER,
        GOLD,
        GREY,
        HOLY,
        RED,
        WHITE,
        YELLOW,
        YOUNG

    }

    public Knight(SpriteSheet spriteSheet) {
        super(spriteSheet);
        position = new Vector2d(50, 133);
        velocity = Vector2d.zero();
        direction = Direction.RIGHT;

        health = 100;
        damage = 50;
        attackRange = 20;
        movementSpeed = 100;

        movementStateMachine.pushState(new KnightFallingState(this, movementStateMachine));
    }

    @Override
    public void draw(Camera camera) {
        camera.drawRectangle(getX(), getY(), getWidth(), getHeight(), Color.RED);
        camera.drawRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), Color.CYAN);
        if (isFacingLeft()) {
            getCurrentAnimation().drawFlippedHorizontally(this, camera);
        } else {
            super.draw(camera);
        }
    }

    @Override
    public void onAttacked(Actor attacker) {

    }

    public void handleInput(Keyboard keyboard) {
        movementStateMachine.handleInput(keyboard);
        if (keyboard.isPressed(KeyBinding.ATTACK)) {
            if (combatStateMachine.isEmpty() || !(combatStateMachine.getCurrentState() instanceof KnightAttackingState)) {
                if (movementStateMachine.getCurrentState() instanceof KnightFallingState) {
                    combatStateMachine.pushState(new KnightAttackingMidAirState(this, combatStateMachine));
                } else {
                    combatStateMachine.pushState(new KnightAttackingState(this, combatStateMachine));
                }
            }
        }
    }

}

