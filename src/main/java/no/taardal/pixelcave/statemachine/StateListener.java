package no.taardal.pixelcave.statemachine;

import no.taardal.pixelcave.state.ActorState;

public interface StateListener {

    void onChangeState(ActorState actorState);

    void onPushState(ActorState actorState);

    void onPopState();

}
