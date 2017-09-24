package no.taardal.blossom.state.actor.naga;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.state.actor.ActorDeadState;
import no.taardal.blossom.state.actor.EnemyState;

public class NagaDeadState extends ActorDeadState<Naga> implements EnemyState {

    public NagaDeadState(Naga actor) {
        super(actor);
    }

    @Override
    public void nextMove(Player player) {

    }

}
