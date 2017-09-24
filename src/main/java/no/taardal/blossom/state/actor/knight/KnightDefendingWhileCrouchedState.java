package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.sprite.Animation;

public class KnightDefendingWhileCrouchedState extends KnightDefendingState {

    public KnightDefendingWhileCrouchedState(Knight actor) {
        super(actor);
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("DEFENDING_WHILE_CROUCHED");
    }

}
