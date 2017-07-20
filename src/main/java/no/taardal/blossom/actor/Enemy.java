package no.taardal.blossom.actor;

import no.taardal.blossom.actorstate.enemystate.EnemyState;
import no.taardal.blossom.world.World;

public abstract class Enemy extends Actor {

    public Enemy(World world) {
        super(world);
    }

    public void nextMove(Player player) {
        ((EnemyState) states.getFirst()).nextMove(player);
    }

}
