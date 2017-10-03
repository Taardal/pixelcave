package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.vector.Vector2d;

public interface Player {

    void handleInput(Keyboard keyboard);

    void update(double secondsSinceLastUpdate);

    void draw(Camera camera);

    Vector2d getPosition();

}
