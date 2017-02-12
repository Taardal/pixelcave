package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;

public interface Level {

    void update(Keyboard keyboard);

    void draw(Camera camera);

}
