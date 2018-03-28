package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.vector.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightJumpingState extends KnightFallingState {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightJumpingState.class);
    private static final int MAX_JUMPING_VELOCITY_Y = 100;
    private static final int JUMPING_VELOCITY_Y = -300;

    public KnightJumpingState(Knight actor, StateListener stateListener) {
        super(actor, stateListener);
    }

    @Override
    public Animation getAnimation() {
        if (actor.getVelocity().getY() != 0 && actor.getVelocity().getY() < MAX_JUMPING_VELOCITY_Y) {
            return actor.getAnimations().get(Animation.Type.JUMP);
        } else {
            return super.getAnimation();
        }
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        Animation animation = getAnimation();
        actor.setVelocity(actor.getVelocity().withY(JUMPING_VELOCITY_Y));
        float boundsX = actor.getDirection() == Direction.RIGHT ? actor.getX() : actor.getX() + actor.getWidth() - animation.getWidth();
        float boundsY = actor.getY() + actor.getHeight() - animation.getHeight();
        actor.setCollisionBounds(new Bounds.Builder()
                .setWidth(animation.getWidth())
                .setHeight(animation.getHeight())
                .setPosition(new Vector2f(boundsX, boundsY))
                .build());
    }

    @Override
    public void onExit() {
        super.onExit();
        actor.getAnimations().get(Animation.Type.JUMP).reset();
    }

}
