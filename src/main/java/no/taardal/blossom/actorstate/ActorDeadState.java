package no.taardal.blossom.actorstate;

import no.taardal.blossom.actor.Actor;

public abstract class ActorDeadState<T extends Actor> implements ActorState {

    protected T actor;

    public ActorDeadState(T actor) {
        this.actor = actor;
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
