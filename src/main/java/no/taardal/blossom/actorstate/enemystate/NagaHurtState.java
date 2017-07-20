package no.taardal.blossom.actorstate.enemystate;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.actorstate.ActorHurtState;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;

import java.awt.*;

public class NagaHurtState extends ActorHurtState<Naga, Actor> implements EnemyState {

    private static final Animation HURT_ANIMATION = getHurtAnimation();
    private static final Rectangle BOUNDS = new Rectangle(20, 34);

    public NagaHurtState(Naga naga, Actor attacker) {
        super(naga, attacker);
    }

    @Override
    public Animation getAnimation() {
        return HURT_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return BOUNDS;
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        if (actor.getHealth() <= 0) {
            actor.pushState(new NagaDeadState(actor));
        } else {
            getAnimation().update();
            if (getAnimation().isFinished()) {
                actor.popState();
            }
        }
    }

    @Override
    public void nextMove(Player player) {

    }

    @Override
    protected void updateBounds() {
        int boundsX = actor.getX() + 22;
        int boundsY = (actor.getY() + actor.getHeight()) - (int) BOUNDS.getHeight();
        BOUNDS.setLocation(boundsX, boundsY);
    }

    private static Animation getHurtAnimation() {
        Sprite[] sprites = new Sprite[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Naga.SPRITE_SHEET.getSprites()[i][4];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;

    }
}
