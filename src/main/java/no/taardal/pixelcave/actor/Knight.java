package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.state.KnightIdleState;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
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
        YOUNG;

    }

    public Knight(SpriteSheet spriteSheet, Direction direction, Vector2f velocity, Vector2f position) {
        super(spriteSheet, direction, velocity, position);
        stateMachine.onPushState(new KnightIdleState(this, stateMachine));
        bounds = new Bounds.Builder()
                .setWidth(getAnimation().getWidth())
                .setHeight(getAnimation().getHeight())
                .setPosition(position)
                .build();
    }

    public void handleInput(Keyboard keyboard) {
        if (!stateMachine.isEmpty()) {
            stateMachine.getCurrentState().handleInput(keyboard);
        } else {
            LOGGER.warn("Could not handle input. State machine was empty.");
        }
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        if (!stateMachine.isEmpty()) {
            stateMachine.getCurrentState().update(world, secondsSinceLastUpdate);
        } else {
            LOGGER.warn("Could not update. State machine was empty.");
        }
    }

    @Override
    public void draw(Camera camera) {
        camera.drawRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), Color.CYAN);
        Animation animation = getAnimation();
        if (animation != null) {
            if (direction == Direction.RIGHT) {
                animation.draw(this, camera);
            } else {
                animation.drawFlippedHorizontally(this, camera);
            }
        } else {
            LOGGER.warn("Could not draw. Animation was null.");
        }
    }

}

