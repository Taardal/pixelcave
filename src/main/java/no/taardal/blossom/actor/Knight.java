package no.taardal.blossom.actor;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.state.actor.knight.KnightAttackingMidAirState;
import no.taardal.blossom.state.actor.knight.KnightAttackingState;
import no.taardal.blossom.state.actor.knight.KnightFallingState;
import no.taardal.blossom.vector.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        position = new Vector2d(0, 133);
        velocity = Vector2d.zero();
        direction = Direction.EAST;

        health = 100;
        damage = 50;
        attackRange = 20;
        movementSpeed = 100;

        movementStateMachine.pushState(new KnightFallingState(this, movementStateMachine));
    }

    @Override
    public void draw(Camera camera) {
        if (direction == Direction.WEST) {
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

