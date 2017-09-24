package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.state.actor.ActorFallingState;
import no.taardal.blossom.state.actor.PlayerState;
import no.taardal.blossom.vector.Vector2d;

public class KnightFallingState extends ActorFallingState<Knight> implements PlayerState {

    public KnightFallingState(Knight actor) {
        super(actor);
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                if (actor.getDirection() != Direction.WEST) {
                    actor.setDirection(Direction.WEST);
                }
                actor.setVelocity(new Vector2d(-actor.getMovementSpeed(), actor.getVelocity().getY()));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                if (actor.getDirection() != Direction.EAST) {
                    actor.setDirection(Direction.EAST);
                }
                actor.setVelocity(new Vector2d(actor.getMovementSpeed(), actor.getVelocity().getY()));
            }
        } else {
            actor.setVelocity(new Vector2d(0, actor.getVelocity().getY()));
        }
        if (keyboard.isPressed(KeyBinding.ATTACK) && actor.getVelocity().getY() >= 0) {

        }
    }

    @Override
    protected void onLanded() {
        if (actor.getVelocity().getX() == 0) {
            if (getAnimation().isFinished()) {
                actor.changeState(new KnightIdleState(actor));
            }
        } else {
            actor.changeState(new KnightIdleState(actor));
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
