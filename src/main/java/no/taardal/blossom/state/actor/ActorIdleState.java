package no.taardal.blossom.state.actor;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.vector.Vector2d;

public abstract class ActorIdleState<T extends Actor> implements ActorState {

    protected T actor;

    public ActorIdleState(T actor) {
        this.actor = actor;
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("IDLE");
    }

    @Override
    public void onEntry() {
        actor.setVelocity(Vector2d.zero());
        updateBounds();
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        getAnimation().update();
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

    @Override
    public String toString() {
        return "ActorIdleState{}";
    }

    protected abstract void updateBounds();

}
