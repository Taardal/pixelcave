package no.taardal.blossom.actor;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.vector.Vector2d;

public interface Player {

    void handleInput(Keyboard keyboard);

    void update(double secondsSinceLastUpdate);

    void draw(Camera camera);

    Vector2d getPosition();

}
