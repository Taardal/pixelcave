package no.taardal.pixelcave.state.actor.knight;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.sprite.Sprite;
import no.taardal.pixelcave.statemachine.StateMachine;

public class KnightAttackingMidAirState extends KnightAttackingState {

    public KnightAttackingMidAirState(Knight actor, StateMachine stateMachine) {
        super(actor, stateMachine);
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        if (getAnimation().isFinished()) {
            stateMachine.popState();
        } else if (!enemiesAttacked && getAnimation().getFrame() == 1) {
            attackEnemiesInRange();
            enemiesAttacked = true;
        }
        getAnimation().update();
    }

    @Override
    protected Animation getActorAnimation() {
        Sprite[] sprites = new Sprite[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = actor.getSpriteSheet().getSprites()[i][11];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(7);
        animation.setIndefinite(false);
        return animation;
    }

}
