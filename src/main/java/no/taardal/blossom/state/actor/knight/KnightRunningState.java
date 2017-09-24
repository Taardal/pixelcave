package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.state.actor.ActorRunningState;
import no.taardal.blossom.state.actor.PlayerState;

public class KnightRunningState extends ActorRunningState<Knight> implements PlayerState {

    public KnightRunningState(Knight actor) {
        super(actor);
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.WEST);
                actor.setVelocity(getVelocity());
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(getVelocity());
            }
        } else {
            actor.changeState(new KnightIdleState(actor));
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            actor.changeState(new KnightJumpingState(actor));
        }
        if (keyboard.isPressed(KeyBinding.CROUCH)) {
        }
        if (keyboard.isPressed(KeyBinding.TUMBLE)) {
        }
        if (keyboard.isPressed(KeyBinding.DEFEND)) {
        }
        if (keyboard.isPressed(KeyBinding.ATTACK)) {
            actor.pushState(new KnightAttackingState(actor));
        }
    }

    @Override
    protected void updateBounds() {
        actor.getBounds().setWidth(27);
        actor.getBounds().setHeight(27);
        actor.getBounds().setPosition(getBoundsX(), getBoundsY());
    }

    private int getBoundsY() {
        return (actor.getY() + actor.getHeight()) - actor.getBounds().getHeight();
    }

    private int getBoundsX() {
        int marginX = 8;
        if (actor.getDirection() == Direction.EAST) {
            return actor.getX() + marginX;
        } else {
            return actor.getX() + actor.getWidth() - actor.getBounds().getWidth() - marginX;
        }
    }

}
