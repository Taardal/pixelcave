package no.taardal.blossom.state.actor;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.sprite.Animation;

public abstract class ActorDeadState<T extends Actor> implements ActorState {

    protected T actor;

    public ActorDeadState(T actor) {
        this.actor = actor;
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("DEATH");
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().isFinished()) {
            actor.setDead(true);
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

}
