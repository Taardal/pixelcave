package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.Key;
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
    public ActorState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A) || keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
            return new WalkingActorState(actor, tiledEditorMap);
        } else {
            return null;
        }
    }

    @Override
    public ActorState update() {
        return null;
    }

    @Override
    public String toString() {
        return "IdleActorState{}";
    }
}
