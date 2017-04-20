package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.keyboard.Keyboard;

public interface ActorState {

    ActorState handleInput(Keyboard keyboard);

    ActorState update();

}
