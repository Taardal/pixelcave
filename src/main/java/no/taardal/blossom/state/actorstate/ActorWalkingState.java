package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActorWalkingState implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorWalkingState.class);

    Actor actor;
    World world;

    public ActorWalkingState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
    }

    @Override
    public void onEntry() {
        if (actor.getDirection() == Direction.WEST) {
            actor.setVelocity(new Vector2d(-200, 0));
        } else if (actor.getDirection() == Direction.EAST) {
            actor.setVelocity(new Vector2d(200, 0));
        }
    }

    @Override
    public ActorState update(double timeSinceLastUpdate) {
        Vector2d vector2d = actor.getVelocity().multiply(timeSinceLastUpdate);
        actor.getPosition().addAssign(vector2d);
        return null;
    }

    @Override
    public String toString() {
        return "ActorWalkingState{}";
    }

}
