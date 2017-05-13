package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.map.TiledEditorMap;

public class IdleActorState implements ActorState {

    private Actor actor;
    private TiledEditorMap tiledEditorMap;

    public IdleActorState(Actor actor, TiledEditorMap tiledEditorMap) {
        this.actor = actor;
        this.tiledEditorMap = tiledEditorMap;
    }

    @Override
    public String toString() {
        return "IdleActorState{}";
    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            return new WalkingActorState(actor, tiledEditorMap);
        } else {
            return null;
        }
    }

    @Override
    public ActorState update() {
        if (!actor.isOnGround()) {
            return new FallingActorState(actor, tiledEditorMap);
        } else {
            return null;
        }
    }

}
