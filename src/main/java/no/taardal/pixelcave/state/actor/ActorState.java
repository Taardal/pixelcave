package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.statemachine.StateMachine;

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

    public void update(float secondsSinceLastUpdate) {
        getAnimation().update();
        updateBounds();
    }

    public void onExit() {
        getAnimation().reset();
    }

    protected abstract Animation getActorAnimation();

    protected abstract void updateBounds();

}
