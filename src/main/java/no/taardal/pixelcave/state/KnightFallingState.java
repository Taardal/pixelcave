package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightFallingState extends MovementState<Knight> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightFallingState.class);
    private static final int TERMINAL_VELOCITY = 300;

    public KnightFallingState(Knight actor, StateListener stateListener) {
        super(actor, stateListener);
    }

    @Override
    public Animation getAnimation() {
        if (actor.getVelocity().getY() != 0) {
            return actor.getAnimations().get(Animation.Type.FALL);
        } else {
            return actor.getAnimations().get(Animation.Type.LAND);
        }
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        actor.setVelocity(actor.getVelocity().withY(25));
        Animation animation = getAnimation();
        float boundsX = actor.getDirection() == Direction.RIGHT ? actor.getX() : actor.getX() + actor.getWidth() - animation.getWidth();
        float boundsY = actor.getY() + actor.getHeight() - animation.getHeight();
        actor.setCollisionBounds(new Bounds.Builder()
                .setWidth(animation.getWidth())
                .setHeight(animation.getHeight())
                .setPosition(new Vector2f(boundsX, boundsY))
                .build());
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                if (actor.getDirection() != Direction.LEFT) {
                    actor.setDirection(Direction.LEFT);
                }
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                if (actor.getDirection() != Direction.RIGHT) {
                    actor.setDirection(Direction.RIGHT);
                }
            }
            int velocityX = actor.getDirection() == Direction.RIGHT ? actor.getMovementSpeed() : -actor.getMovementSpeed();
            actor.setVelocity(actor.getVelocity().withX(velocityX));
        } else {
            if (actor.getVelocity().getX() != 0) {
                actor.setVelocity(new Vector2f(0, actor.getVelocity().getY()));
            }
        }
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        getAnimation().update();

        Bounds collisionBounds = actor.getCollisionBounds();
        float boundsX = actor.getDirection() == Direction.RIGHT ? actor.getX() : actor.getX() + actor.getWidth() - collisionBounds.getWidth();
        collisionBounds.setPosition(collisionBounds.getPosition().withX(boundsX));

        Vector2f position = collisionBounds.getPosition();
        Vector2f nextPosition = position.add(actor.getVelocity().multiply(secondsSinceLastUpdate));

        int tw = world.getTileWidth();
        int th = world.getTileHeight();

        int minRow = ((int) position.getY()) / th;
        int maxRow = (((int) position.getY()) + collisionBounds.getHeight()) / th;

        float px = position.getX();
        float npx = nextPosition.getX();
        float dx = npx - px;
        if (dx < 0) {
            dx = -dx;
        }

        int column = ((int) px) / tw;

        float dct = Integer.MAX_VALUE;
        for (int r = minRow; r < maxRow; r++) {
            for (int c = column; c >= 0; c--) {
                if (isSolidTile(c, r, world)) {
                    int tx = (c * tw) + tw;
                    float dt = tx - px;
                    if (dt < 0) {
                        dt = -dt;
                    }
                    dct = Math.min(dt, dct);
                }
            }
        }


        if (dct < Integer.MAX_VALUE) {
            float d = Math.min(dx, dct);
            Vector2f distanceToMove = new Vector2f(d, 0);
            LOGGER.info("Distance to move [{}]", distanceToMove);

            if (actor.getDirection() == Direction.LEFT) {
                actor.getSpriteBounds().setPosition(actor.getPosition().subtract(distanceToMove));
                collisionBounds.setPosition(collisionBounds.getPosition().subtract(distanceToMove));
            } else {
                actor.getSpriteBounds().setPosition(actor.getPosition().add(distanceToMove));
                collisionBounds.setPosition(collisionBounds.getPosition().add(distanceToMove));
            }
        }
    }

    @Override
    public void onExit() {
        actor.getAnimations().get(Animation.Type.FALL).reset();
        actor.getAnimations().get(Animation.Type.LAND).reset();
    }
}
