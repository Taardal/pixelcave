package no.taardal.blossom.gamestate;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;

public interface GameState {

    GameState update(double secondsSinceLastUpdate, Keyboard keyboard, Camera camera);
    void draw(Camera camera);

}
