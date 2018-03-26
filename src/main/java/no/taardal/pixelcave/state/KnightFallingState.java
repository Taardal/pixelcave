package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
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
            return actor.getSpriteSheet().getAnimations().get(Animation.Type.FALL);
        } else {
            return actor.getSpriteSheet().getAnimations().get(Animation.Type.LAND);
        }
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        actor.setVelocity(actor.getVelocity().withY(25));
        Animation animation = getAnimation();
        float boundsX = actor.getX() + actor.getWidth() - animation.getWidth();
        float boundsY = actor.getY() + actor.getHeight() - animation.getHeight();
        actor.setCollisionBounds(new Bounds.Builder()
                .setWidth(animation.getWidth())
                .setHeight(animation.getHeight())
                .setPosition(new Vector2f(boundsX, boundsY))
                .build());
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        float velocityY = actor.getVelocity().getY() + (World.GRAVITY * secondsSinceLastUpdate);
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
        actor.setVelocity(actor.getVelocity().withY(velocityY));

        Vector2f nextCollisionBoundsPosition = actor.getCollisionBounds().getPosition().add(actor.getVelocity().multiply(secondsSinceLastUpdate));
        nextCollisionBoundsPosition = checkHorizontalCollision(nextCollisionBoundsPosition, world);
        nextCollisionBoundsPosition = checkVerticalCollision(nextCollisionBoundsPosition, world);
        Vector2f distanceToMove = nextCollisionBoundsPosition.subtract(actor.getCollisionBounds().getPosition());
        actor.setPosition(actor.getPosition().add(distanceToMove));
        actor.getCollisionBounds().setPosition(actor.getCollisionBounds().getPosition().add(distanceToMove));

        getAnimation().update();
        if (actor.getVelocity().getY() == 0) {
            if (getAnimation().isFinished()) {
                stateListener.onChangeState(new KnightIdleState(actor, stateListener));
            }
        }
    }

    @Override
    public void onExit() {
        actor.getSpriteSheet().getAnimations().get(Animation.Type.FALL).reset();
        actor.getSpriteSheet().getAnimations().get(Animation.Type.LAND).reset();
    }
}
