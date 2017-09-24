package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.state.actor.PlayerState;
import no.taardal.blossom.vector.Vector2d;

public class KnightTumblingState implements PlayerState {

    private Knight actor;

    public KnightTumblingState(Knight actor) {
        this.actor = actor;
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("TUMBLING");
    }

    @Override
    public void onEntry() {
        if (actor.getDirection() == Direction.EAST) {
            actor.setVelocity(new Vector2d(100, actor.getVelocity().getY()));
        } else {
            actor.setVelocity(new Vector2d(-100, actor.getVelocity().getY()));
        }
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().getFrame() > 2) {
            Vector2d distance = actor.getVelocity().multiply(timeSinceLastUpdate);
            actor.setPosition(actor.getPosition().add(distance));
        }
        if (getAnimation().isFinished()) {
            actor.changeState(new KnightIdleState(actor));
        }
        updateBounds();
    }

    @Override
    public void onExit() {
        actor.getAnimations().get("TUMBLING").reset();
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
