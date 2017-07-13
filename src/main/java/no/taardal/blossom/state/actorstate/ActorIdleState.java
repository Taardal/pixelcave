package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class ActorIdleState implements ActorState {

    Actor actor;
    World world;

    public ActorIdleState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
    }

    @Override
    public void onEntry() {
        actor.setVelocity(Vector2d.zero());
    }

    @Override
    public ActorState update(double timeSinceLastUpdate) {
        return null;
    }

    @Override
    public String toString() {
        return "ActorIdleState{}";
    }

}
