package no.taardal.pixelcave.statemachine;

import no.taardal.pixelcave.state.ActorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;

public class StateMachine implements StateListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateMachine.class);

    private Deque<ActorState> actorStateDeque;

    public StateMachine() {
        actorStateDeque = new ArrayDeque<>();
    }

    @Override
    public void onChangeState(ActorState actorState) {
        if (!isEmpty()) {
            onPopState();
        }
        onPushState(actorState);
    }

    @Override
    public void onPopState() {
        actorStateDeque.getFirst().onExit();
        actorStateDeque.removeFirst();
    }

    @Override
    public void onPushState(ActorState actorState) {
        actorState.onEntry();
        actorStateDeque.addFirst(actorState);
    }

    public ActorState getCurrentState() {
        return !isEmpty() ? actorStateDeque.getFirst() : null;
    }

    public boolean isEmpty() {
        return actorStateDeque.isEmpty();
    }

}
