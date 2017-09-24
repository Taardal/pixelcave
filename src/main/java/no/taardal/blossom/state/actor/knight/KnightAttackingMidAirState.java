package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.sprite.Animation;

public class KnightAttackingMidAirState extends KnightAttackingState {

    public KnightAttackingMidAirState(Knight actor) {
        super(actor);
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("ATTACKING_MID_AIR");
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().isFinished()) {
            actor.popState();
        } else if (!enemiesAttacked && getAnimation().getFrame() == 1) {
            attackEnemiesInRange();
            enemiesAttacked = true;
        }
    }

}
