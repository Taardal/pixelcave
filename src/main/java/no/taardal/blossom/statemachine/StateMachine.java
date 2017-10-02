package no.taardal.blossom.statemachine;

import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.state.actor.ActorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;

public class StateMachine {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateMachine.class);

    private Deque<ActorState> actorStateDeque;

    public StateMachine() {
        actorStateDeque = new ArrayDeque<>();
    }

    public void handleInput(Keyboard keyboard) {
        if (!isEmpty()) {
            actorStateDeque.getFirst().nextMove(keyboard);
        }
    }

    public void update(double secondsSinceLastUpdate) {
        if (!isEmpty()) {
            actorStateDeque.getFirst().update(secondsSinceLastUpdate);
        }
    }

    public void changeState(ActorState actorState) {
        if (!isEmpty()) {
            popState();
        }
        pushState(actorState);
    }

    public void popState() {
        actorStateDeque.getFirst().onExit();
        actorStateDeque.removeFirst();
    }

    public void pushState(ActorState actorState) {
        actorState.onEntry();
        actorStateDeque.addFirst(actorState);
    }

    public ActorState getCurrentState() {
        return actorStateDeque.getFirst();
    }

    public boolean isEmpty() {
        return actorStateDeque.isEmpty();
    }
}
