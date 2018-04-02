package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;

public interface Player {

    void handleInput(Keyboard keyboard);

    void update(World world, float secondsSinceLastUpdate);

    void draw(Camera camera);

    Vector2f getPosition();

    Direction getDirection();

}
