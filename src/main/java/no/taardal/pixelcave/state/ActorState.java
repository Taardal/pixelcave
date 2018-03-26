package no.taardal.pixelcave.state;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.world.World;

public interface ActorState {

    Animation getAnimation();

    void onEntry();

    void handleInput(Keyboard keyboard);

    void update(World world, float secondsSinceLastUpdate);

    void onExit();
}
