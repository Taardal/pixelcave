package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;

import java.awt.*;

public class PlayerAttackingWhileCrouchedState implements PlayerState {

    private static final Animation ATTACKING_WHILE_CROUCHED_ANIMATION = getAttackingWhileCrouchedAnimation();

    private Player player;
    private World world;
    private Rectangle attackBounds;
    private boolean enemiesAttacked;

    public PlayerAttackingWhileCrouchedState(Player player, World world) {
        this.player = player;
        this.world = world;
        attackBounds = getAttackBounds();
    }

    @Override
    public Animation getAnimation() {
        return ATTACKING_WHILE_CROUCHED_ANIMATION;
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
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    private static Animation getAttackingWhileCrouchedAnimation() {
        Sprite[] sprites = new Sprite[8];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][4];
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
