package no.taardal.blossom.actorstate.enemystate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.actorstate.ActorState;

public interface EnemyState extends ActorState {

    void nextMove(Player player);

}
