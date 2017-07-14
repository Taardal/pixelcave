package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public abstract class ActorIdleState implements ActorState {

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
    public void update(double timeSinceLastUpdate) {

    }

    @Override
    public void onExit() {

    }

    @Override
    public String toString() {
        return "ActorIdleState{}";
    }

}
