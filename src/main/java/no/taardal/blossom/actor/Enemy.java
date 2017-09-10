package no.taardal.blossom.actor;

import no.taardal.blossom.actorstate.enemystate.EnemyState;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.world.World;

public abstract class Enemy extends Actor {

    public Enemy(SpriteSheet spriteSheet) {
        super(spriteSheet);
    }

    public Enemy(World world, SpriteSheet spriteSheet) {
        super(world, spriteSheet);
    }

    public void nextMove(Player player) {
        ((EnemyState) states.getFirst()).nextMove(player);
    }

}
