package no.taardal.blossom.actorstate.enemystate;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actorstate.ActorIdleState;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NagaIdleState extends ActorIdleState<Naga> implements EnemyState {

    private static final Logger LOGGER = LoggerFactory.getLogger(NagaIdleState.class);
    private static final Animation IDLE_ANIMATION = getIdleAnimation();

    public NagaIdleState(Naga naga, World world) {
        super(naga, world);
    }

    @Override
    public Animation getAnimation() {
        return IDLE_ANIMATION;
    }

    @Override
    public void nextMove() {

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
