package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.state.actor.ActorIdleState;
import no.taardal.blossom.state.actor.PlayerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightIdleState extends ActorIdleState<Knight> implements PlayerState {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightIdleState.class);

    public KnightIdleState(Knight actor) {
        super(actor);
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            actor.pushState(new KnightRunningState(actor));
        } else if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            actor.pushState(new KnightJumpingState(actor));
        } else if (keyboard.isPressed(KeyBinding.CROUCH)) {

        } else if (keyboard.isPressed(KeyBinding.TUMBLE)) {
        } else if (keyboard.isPressed(KeyBinding.DEFEND)) {
        } else if (keyboard.isPressed(KeyBinding.ATTACK)) {
            actor.pushState(new KnightAttackingState(actor));
        }
    }

    @Override
    protected void updateBounds() {
        actor.getBounds().setWidth(19);
        actor.getBounds().setHeight(30);
        actor.getBounds().setPosition(getBoundsX(), getBoundsY());
    }

    private int getBoundsY() {
        return (actor.getY() + actor.getHeight()) - actor.getBounds().getHeight();
    }

    private int getBoundsX() {
        int marginX = 5;
        if (actor.getDirection() == Direction.EAST) {
            return actor.getX() + marginX;
        } else {
            return actor.getX() + actor.getWidth() - actor.getBounds().getWidth() - marginX;
        }
    }

}
