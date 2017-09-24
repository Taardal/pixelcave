package no.taardal.blossom.state.actor;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.vector.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActorRunningState<T extends Actor> implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorRunningState.class);

    protected T actor;
    protected Vector2d distanceWalked;

    public ActorRunningState(T actor) {
        this.actor = actor;
        distanceWalked = Vector2d.zero();
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("RUNNING");
    }

    @Override
    public void onEntry() {
        actor.setVelocity(getVelocity());
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        getAnimation().update();
        distanceWalked = actor.getVelocity().multiply(secondsSinceLastUpdate);
        actor.setPosition(actor.getPosition().add(distanceWalked));
        updateBounds();
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

    protected abstract void updateBounds();

    protected Vector2d getVelocity() {
        if (actor.getDirection() == Direction.WEST) {
            return new Vector2d(-actor.getMovementSpeed(), actor.getVelocity().getY());
        } else {
            return new Vector2d(actor.getMovementSpeed(), actor.getVelocity().getY());
        }
    }

}
