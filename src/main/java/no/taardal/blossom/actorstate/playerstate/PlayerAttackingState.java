package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class PlayerAttackingState implements PlayerState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerAttackingState.class);
    private static final Animation ATTACK_ANIMATION = getAttackAnimation();

    private Player player;
    private Rectangle attackBounds;
    private boolean enemiesAttacked;

    public PlayerAttackingState(Player player) {
        this.player = player;
        attackBounds = getAttackBounds();
    }

    @Override
    public Animation getAnimation() {
        return ATTACK_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return attackBounds;
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().isFinished()) {
            player.popState();
        } else if (!enemiesAttacked && getAnimation().getFrame() == 4) {
            attackEnemiesInRange();
            enemiesAttacked = true;
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
        enemiesAttacked = false;
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

    private Rectangle getAttackBounds() {
        int width = 20;
        int height = (int) (player.getBounds().getHeight() / 2) + 5;
        int y = (int) (player.getBounds().getY());
        int x;
        if (player.getDirection() == Direction.EAST) {
            x = (int) (player.getBounds().getX() + (player.getBounds().getWidth() / 2));
        } else {
            x = (int) player.getBounds().getX() - 10;
        }
        return new Rectangle(x, y, width, height);
    }

    private void attackEnemiesInRange() {
        for (int i = 0; i < player.getEnemies().size(); i++) {
            Actor enemy = player.getEnemies().get(i);
            if (attackBounds.intersects(enemy.getBounds())) {
                enemy.onAttacked(player);
            }
        }
    }

}
