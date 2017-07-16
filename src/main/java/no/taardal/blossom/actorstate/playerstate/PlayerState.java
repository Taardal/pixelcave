package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actorstate.ActorState;
import no.taardal.blossom.keyboard.Keyboard;

public interface PlayerState extends ActorState {

    void handleInput(Keyboard keyboard);

}
