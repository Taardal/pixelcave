package no.taardal.blossom.state.actor;

import no.taardal.blossom.sprite.Animation;

public interface ActorState {

    Animation getAnimation();

    void onEntry();

    void update(double secondsSinceLastUpdate);

    void onExit();

}
