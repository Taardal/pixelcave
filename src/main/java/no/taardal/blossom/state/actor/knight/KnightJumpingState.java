package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.vector.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightJumpingState extends KnightFallingState {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightJumpingState.class);
    private static final int VELOCITY_Y = -200;

    public KnightJumpingState(Knight actor) {
        super(actor);
    }

    @Override
    public Animation getAnimation() {
        if (falling && actor.getVelocity().getY() < 100) {
            return actor.getAnimations().get("JUMPING");
        } else {
            return super.getAnimation();
        }
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setVelocity(new Vector2d(actor.getVelocity().getX(), VELOCITY_Y));
    }

    @Override
    public void onExit() {
        super.onExit();
        actor.getAnimations().get("JUMPING").reset();
    }

    @Override
    protected void updateBounds() {
        super.updateBounds();
        if (falling && actor.getVelocity().getY() < 100) {
            actor.getBounds().setPosition(actor.getBounds().getX(), actor.getBounds().getY() - 5);
        }
    }

}
