package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.state.actor.PlayerState;

public class KnightCrouchingState implements PlayerState {

    private Knight actor;

    public KnightCrouchingState(Knight actor) {
        this.actor = actor;
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("CROUCHING");
    }

    @Override
    public void onEntry() {
        updateBounds();
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        getAnimation().update();
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (!keyboard.isPressed(KeyBinding.CROUCH)) {
        } else if (keyboard.isPressed(KeyBinding.ATTACK)) {
        } else if (keyboard.isPressed(KeyBinding.DEFEND)) {
        }
    }

    private void updateBounds() {
        actor.getBounds().setWidth(19);
        actor.getBounds().setHeight(20);
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
