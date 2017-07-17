package no.taardal.blossom.actorstate.enemystate;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;

public class NagaAttackingState implements EnemyState {

    private static final Animation ATTACK_ANIMATION = getAttackAnimation();

    private Naga naga;

    public NagaAttackingState(Naga naga, World world) {
        this.naga = naga;
    }

    @Override
    public Animation getAnimation() {
        return ATTACK_ANIMATION;
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().isFinished()) {
            naga.popState();
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }



    @Override
    public void nextMove() {

    }

    @Override
    public String toString() {
        return "NagaAttackingState{}";
    }

    private static Animation getAttackAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Naga.SPRITE_SHEET.getSprites()[i][2];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(true);
        return animation;
    }
}
