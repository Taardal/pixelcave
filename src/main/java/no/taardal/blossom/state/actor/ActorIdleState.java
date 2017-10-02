package no.taardal.blossom.state.actor;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.statemachine.StateMachine;
import no.taardal.blossom.vector.Vector2d;

public abstract class ActorIdleState<T extends Actor> extends ActorState<T> {

    public ActorIdleState(T actor, StateMachine stateMachine) {
        super(actor, stateMachine);
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

}
