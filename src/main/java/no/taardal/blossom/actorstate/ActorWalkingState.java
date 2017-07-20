package no.taardal.blossom.actorstate;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActorWalkingState<T extends Actor> implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorWalkingState.class);

    protected T actor;
    protected World world;
    protected Vector2d distanceWalked;

    public ActorWalkingState(T actor, World world) {
        this.actor = actor;
        this.world = world;
        distanceWalked = Vector2d.zero();
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

    @Override
    public String toString() {
        return "ActorWalkingState{}";
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
