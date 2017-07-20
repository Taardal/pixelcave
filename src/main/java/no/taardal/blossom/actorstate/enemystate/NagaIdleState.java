package no.taardal.blossom.actorstate.enemystate;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.actorstate.ActorIdleState;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class NagaIdleState extends ActorIdleState<Naga> implements EnemyState {

    private static final Logger LOGGER = LoggerFactory.getLogger(NagaIdleState.class);
    private static final Animation IDLE_ANIMATION = getIdleAnimation();
    private static final Rectangle BOUNDS = new Rectangle(20, 34);

    public NagaIdleState(Naga naga, World world) {
        super(naga, world);
    }

    @Override
    public Animation getAnimation() {
        return IDLE_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return BOUNDS;
    }

    @Override
    public void nextMove(Player player) {
        int radius = 20;
        double length = Vector2d.getLength(actor.getPosition(), player.getPosition());
        if (length <= radius) {
            actor.pushState(new NagaAttackingState(actor, world));
        } else {
            actor.changeState(new NagaWalkingState(actor, world));
        }
    }

    @Override
    public String toString() {
        return "NagaIdleState{}";
    }

    @Override
    protected void updateBounds() {
        int boundsX = actor.getX() + 22;
        int boundsY = (actor.getY() + actor.getHeight()) - (int) BOUNDS.getHeight();
        BOUNDS.setLocation(boundsX, boundsY);
    }

    private static Animation getIdleAnimation() {
        Sprite[] sprites = new Sprite[6];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Naga.SPRITE_SHEET.getSprites()[i][0];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(10);
        return animation;
    }

}
