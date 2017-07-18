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

    public ActorWalkingState(T actor, World world) {
        this.actor = actor;
        this.world = world;
    }

    @Override
    public void onEntry() {
        if (actor.getDirection() == Direction.WEST) {
            actor.setVelocity(new Vector2d(-getVelocityX(), actor.getVelocity().getY()));
        } else if (actor.getDirection() == Direction.EAST) {
            actor.setVelocity(new Vector2d(getVelocityX(), actor.getVelocity().getY()));
        }
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
        Vector2d distance = actor.getVelocity().multiply(timeSinceLastUpdate);
        actor.setPosition(actor.getPosition().add(distance));
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

    protected abstract int getVelocityX();

}
