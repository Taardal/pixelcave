package no.taardal.blossom.actor;

import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.state.actor.EnemyState;

public abstract class Enemy extends Actor {

    public Enemy(SpriteSheet spriteSheet) {
        super(spriteSheet);
    }

    public void nextMove(Player player) {
        ((EnemyState) states.getFirst()).nextMove(player);
    }

}
