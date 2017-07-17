package no.taardal.blossom.actorstate;

import no.taardal.blossom.sprite.Animation;

public interface ActorState {

    Animation getAnimation();

    void onEntry();

    void update(double timeSinceLastUpdate);

    void onExit();

}
