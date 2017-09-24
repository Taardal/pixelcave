package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.sprite.Animation;

public class KnightAttackingWhileCrouchedState extends KnightAttackingState {

    public KnightAttackingWhileCrouchedState(Knight actor) {
        super(actor);
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("ATTACKING_WHILE_CROUCHED");
    }

}
