package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.statemachine.StateMachine;

public abstract class ActorState<T extends Actor> {

    protected T actor;
    protected StateMachine stateMachine;

    public ActorState(T actor, StateMachine stateMachine) {
        this.actor = actor;
        this.stateMachine = stateMachine;
    }

    public abstract void onEntry();

    public abstract void handleInput(Keyboard keyboard);

    public void update(float secondsSinceLastUpdate) {
        getAnimation().update();
    }

    public void onExit() {
        getAnimation().reset();
    }

    public abstract Animation getAnimation();

}
