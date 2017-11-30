package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.sprite.SpriteSheet;

public abstract class Enemy extends Actor {

    public Enemy(SpriteSheet spriteSheet) {
        super(spriteSheet);
    }

    public void nextMove(Player player) {
        if (!movementStateMachine.isEmpty()) {
            //((EnemyState) movementStateMachine.getCurrentState()).handleInput(player);
        }
    }

}
