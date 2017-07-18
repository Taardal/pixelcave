package no.taardal.blossom.actorstate.enemystate;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actorstate.ActorDeadState;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;

import java.awt.*;

public class NagaDeadState extends ActorDeadState<Naga> implements EnemyState {

    private static final Animation DEATH_ANIMATION = getDeathAnimation();

    public NagaDeadState(Naga actor) {
        super(actor);
    }

    @Override
    public Animation getAnimation() {
        return DEATH_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void nextMove() {

    }

    private static Animation getDeathAnimation() {
        Sprite[] sprites = new Sprite[11];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Naga.SPRITE_SHEET.getSprites()[i][6];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }
}
