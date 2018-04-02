package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightRunningState extends MovementState<Knight> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightRunningState.class);

    public KnightRunningState(Knight actor, StateListener stateListener) {
        super(actor, stateListener);
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get(Animation.Type.RUN);
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        actor.getVelocity().setX(actor.getMovementSpeed());
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.LEFT);
                actor.getVelocity().setX(-actor.getMovementSpeed());
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.RIGHT);
                actor.getVelocity().setX(actor.getMovementSpeed());
            }
        } else {
            stateListener.onChangeState(new KnightIdleState(actor, stateListener));
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            stateListener.onChangeState(new KnightJumpingState(actor, stateListener));
        }
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        getAnimation().update();
        step(world, secondsSinceLastUpdate);
        if (!isStandingOnSolidTile(world)) {
            stateListener.onChangeState(new KnightFallingState(actor, stateListener));
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

}
