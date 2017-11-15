package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.vector.Vector2f;

public interface Player {

    void handleInput(Keyboard keyboard);

    void update(float secondsSinceLastUpdate);

    void draw(Camera camera);

    Vector2f getPosition();

}
