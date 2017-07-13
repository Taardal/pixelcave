package no.taardal.blossom.state.actorstate;

public interface ActorState {

    void onEntry();

    ActorState update(double timeSinceLastUpdate);

}
