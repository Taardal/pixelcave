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
    }

    public void handleInput(Keyboard keyboard) {
        stateMachine.getCurrentState().handleInput(keyboard);
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        stateMachine.update(world, secondsSinceLastUpdate);
    }

    @Override
    public void draw(Camera camera) {
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

