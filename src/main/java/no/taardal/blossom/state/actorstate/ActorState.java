package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.keyboard.Keyboard;

public interface ActorState {

    void onEntry();

    ActorState handleInput(Keyboard keyboard);

    ActorState update(double timeSinceLastUpdate);

}
