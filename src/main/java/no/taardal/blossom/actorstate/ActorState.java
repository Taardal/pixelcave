package no.taardal.blossom.actorstate;

import no.taardal.blossom.sprite.Animation;

import java.awt.*;

public interface ActorState {

    Animation getAnimation();

    Rectangle getBounds();

    void onEntry();

    void update(double secondsSinceLastUpdate);

    void onExit();

}
