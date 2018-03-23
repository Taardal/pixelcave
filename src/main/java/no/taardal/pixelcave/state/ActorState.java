package no.taardal.pixelcave.state;

import no.taardal.pixelcave.world.World;

public interface ActorState {

    void onEntry();

    void update(World world, float secondsSinceLastUpdate);

    void onExit();

}
