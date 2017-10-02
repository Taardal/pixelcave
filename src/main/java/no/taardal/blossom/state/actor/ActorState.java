package no.taardal.blossom.state.actor;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.animation.Animation;
import no.taardal.blossom.statemachine.StateMachine;

public abstract class ActorState<T extends Actor> {

    protected T actor;
    protected StateMachine stateMachine;
    protected Animation animation;

    ActorState(T actor, StateMachine stateMachine) {
        this.actor = actor;
        this.stateMachine = stateMachine;
        animation = getActorAnimation();
    }

    public Animation getAnimation() {
        return animation;
    }

    public abstract void onEntry();

    public abstract void nextMove(Keyboard keyboard);

    public void update(double secondsSinceLastUpdate) {
        getAnimation().update();
        updateBounds();
    }

    public void onExit() {
        getAnimation().reset();
    }

    protected abstract Animation getActorAnimation();

    protected abstract void updateBounds();

}
