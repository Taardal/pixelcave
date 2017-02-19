package no.taardal.blossom.manager;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;

public interface Manager<T> {

    void update(Keyboard keyboard);
    void draw(Camera camera);

}
