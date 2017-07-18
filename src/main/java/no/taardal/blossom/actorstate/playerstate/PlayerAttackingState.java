package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerAttackingState implements PlayerState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerAttackingState.class);
    private static final Animation ATTACK_ANIMATION = getAttackAnimation();
    private static final int RANGE = 20;

    private Player player;
    private boolean attacked;

    public PlayerAttackingState(Player player) {
        this.player = player;
    }

    @Override
    public Animation getAnimation() {
        return ATTACK_ANIMATION;
    }

    @Override
    public void onEntry() {

    }

    private boolean isInRange(Actor enemy) {
        double enemyLeftX = enemy.getPosition().getX();
        double enemyRightX = enemyLeftX + enemy.getWidth();
        double playerLeftX = player.getPosition().getX();
        double playerRightX = playerLeftX + player.getWidth();
        if (player.getDirection() == Direction.EAST) {
            return playerRightX < enemyLeftX && (playerRightX + RANGE) > enemyLeftX;
        } else {
            return playerLeftX > enemyRightX && (playerLeftX - RANGE) < enemyRightX;
        }
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().isFinished()) {
            player.popState();
        } else if (!attacked && getAnimation().getFrame() == 4) {
            attackEnemiesInRange();
            attacked = true;
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
        attacked = false;
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    @Override
    public String toString() {
        return "PlayerAttackingState{}";
    }

    private static Animation getAttackAnimation() {
        Sprite[] sprites = new Sprite[9];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][1];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(3);
        animation.setIndefinite(false);
        return animation;
    }

    private void attackEnemiesInRange() {
        for (int i = 0; i < player.getEnemies().size(); i++) {
            Actor enemy = player.getEnemies().get(i);
            if (isInRange(enemy)) {
                enemy.onAttacked(player);
            }
        }
    }

}
