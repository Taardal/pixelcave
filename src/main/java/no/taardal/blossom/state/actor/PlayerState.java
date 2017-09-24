package no.taardal.blossom.state.actor;

import no.taardal.blossom.keyboard.Keyboard;

public interface PlayerState extends ActorState {

    void handleInput(Keyboard keyboard);

}
