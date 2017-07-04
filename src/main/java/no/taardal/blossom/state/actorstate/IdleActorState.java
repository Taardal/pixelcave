package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.world.World;

public class IdleActorState implements ActorState {

    private Actor actor;
    private World world;

    public IdleActorState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
    }

    @Override
    public void onEntry() {

    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            return new WalkingActorState(actor, world);
        } else {
            return null;
        }
    }

    @Override
    public ActorState update(double timeSinceLastUpdate) {
        return null;
    }

    @Override
    public String toString() {
        return "IdleActorState{}";
    }

}