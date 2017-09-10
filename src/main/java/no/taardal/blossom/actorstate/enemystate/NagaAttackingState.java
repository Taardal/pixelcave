package no.taardal.blossom.actorstate.enemystate;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;

import java.awt.*;

public class NagaAttackingState implements EnemyState {

    private static final Animation ATTACK_ANIMATION = getAttackAnimation();

    private Naga actor;
    private Rectangle attackBounds;
    private boolean enemiesAttacked;

    public NagaAttackingState(Naga actor, World world) {
        this.actor = actor;
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
            actor.popState();
        } else if (!enemiesAttacked && getAnimation().getFrame() == 3) {
            /*
            if (attackBounds.intersects(enemy.getBounds())) {
                enemy.onAttacked(actor);
            }
            */
            enemiesAttacked = true;
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }



    @Override
    public void nextMove(Player player) {
        if (actor.getPosition().getX() < player.getPosition().getX()) {
            actor.setDirection(Direction.EAST);
        } else if (actor.getPosition().getX() > player.getPosition().getX()) {
            actor.setDirection(Direction.WEST);
        }
    }

    @Override
    public String toString() {
        return "NagaAttackingState{}";
    }

    private static Animation getAttackAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            //sprites[i] = Naga.SPRITE_SHEET.getSprites()[i][2];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

    private Rectangle getAttackBounds() {
        int width = 20;
        int height = (int) (actor.getBounds().getHeight() / 2) + 5;
        int y = (int) (actor.getBounds().getY());
        int x;
        if (actor.getDirection() == Direction.EAST) {
            x = (int) (actor.getBounds().getX() + (actor.getBounds().getWidth() / 2));
        } else {
            x = (int) actor.getBounds().getX() - 10;
        }
        return new Rectangle(x, y, width, height);
    }

}
