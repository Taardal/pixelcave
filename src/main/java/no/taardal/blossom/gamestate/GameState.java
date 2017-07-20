package no.taardal.blossom.gamestate;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;

public interface GameState {

    GameState handleInput(Keyboard keyboard);
    GameState update(double secondsSinceLastUpdate, Camera camera);
    void draw(Camera camera);

}
