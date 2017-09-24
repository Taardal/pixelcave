package no.taardal.blossom.state.actor;

import no.taardal.blossom.actor.Player;

public interface EnemyState extends ActorState {

    void nextMove(Player player);

}
