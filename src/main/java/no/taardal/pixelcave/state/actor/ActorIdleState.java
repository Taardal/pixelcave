package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.vector.Vector2f;

public abstract class ActorIdleState<T extends Actor> extends ActorState<T> {

    public ActorIdleState(T actor, StateMachine stateMachine) {
        super(actor, stateMachine);
    }

    @Override
    public void onEntry() {
        actor.setVelocity(Vector2f.zero());
        updateBounds();
    }

    @Override
    public void update(float secondsSinceLastUpdate) {
        getAnimation().update();
    }

}
