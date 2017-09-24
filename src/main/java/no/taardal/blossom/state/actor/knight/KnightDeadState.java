package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.state.actor.ActorDeadState;
import no.taardal.blossom.state.actor.PlayerState;

public class KnightDeadState extends ActorDeadState<Knight> implements PlayerState {

    public KnightDeadState(Knight actor) {
        super(actor);
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

}
