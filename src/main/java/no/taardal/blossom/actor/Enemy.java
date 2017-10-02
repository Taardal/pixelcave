package no.taardal.blossom.actor;

import no.taardal.blossom.sprite.SpriteSheet;

public abstract class Enemy extends Actor {

    public Enemy(SpriteSheet spriteSheet) {
        super(spriteSheet);
    }

    public void nextMove(Player player) {
        if (!movementStateMachine.isEmpty()) {
            //((EnemyState) movementStateMachine.getCurrentState()).nextMove(player);
        }
    }

}
