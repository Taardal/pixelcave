package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
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
    private static final int TERMINAL_VELOCITY = 300;
    private static final int JUMPING_VELOCITY = -200;
    private static final int MOVEMENT_SPEED = 100;

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

    public Knight(SpriteSheet spriteSheet, World world, Vector2f position, Vector2f velocity, Direction direction) {
        super(spriteSheet, world, position, velocity, direction);
        stateMachine.onPushState(new KnightIdleState(this, stateMachine));
    }

    public void handleInput(Keyboard keyboard) {

    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        stateMachine.update(world, secondsSinceLastUpdate);
    }

    @Override
    public void draw(Camera camera) {
        camera.drawRectangle(getX(), getY(), getWidth(), getHeight(), Color.RED);
        camera.drawRectangle(boundsPosition.getX(), boundsPosition.getY(), boundsWidth, boundsHeight, Color.CYAN);
        Animation animation = getAnimation();
        if (animation != null) {
            if (direction == Direction.LEFT) {
                animation.drawFlippedHorizontally(this, camera);
            } else {
                super.draw(camera);
            }
        }
    }

}

