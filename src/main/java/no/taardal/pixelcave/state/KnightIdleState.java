package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightIdleState extends MovementState<Knight> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightIdleState.class);

    public KnightIdleState(Knight actor, StateListener stateListener) {
        super(actor, stateListener);
    }

    @Override
    public Animation getAnimation() {
        return actor.getSpriteSheet().getAnimations().get(Animation.Type.IDLE);
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        actor.setVelocity(new Vector2f(0, 0));
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
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT) && actor.getVelocity().getY() == 0) {
            stateListener.onChangeState(new KnightJumpingState(actor, stateListener));
        }
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        int bottomRow = (((int) actor.getCollisionBounds().getY())  + actor.getCollisionBounds().getHeight()) / world.getTileHeight();
        if (isVerticalCollision(bottomRow, world)) {
            getAnimation().update();
        } else {
            stateListener.onChangeState(new KnightFallingState(actor, stateListener));
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }
}
