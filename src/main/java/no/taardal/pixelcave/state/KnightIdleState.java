package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
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
        return actor.getAnimations().get(Animation.Type.IDLE);
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        actor.setVelocity(new Vector2f(0, 0));
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            stateListener.onChangeState(new KnightRunningState(actor, stateListener));
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT) && actor.getVelocity().getY() == 0) {
            stateListener.onChangeState(new KnightJumpingState(actor, stateListener));
        }
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        int bottomRow = (((int) actor.getBounds().getY())  + actor.getBounds().getHeight() + 1) / world.getTileHeight();
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
