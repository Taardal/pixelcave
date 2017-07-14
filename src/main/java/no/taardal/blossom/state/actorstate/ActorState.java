package no.taardal.blossom.state.actorstate;

public interface ActorState {

    void onEntry();

    void update(double timeSinceLastUpdate);

    void onExit();

}
