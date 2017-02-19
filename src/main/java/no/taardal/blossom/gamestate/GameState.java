package no.taardal.blossom.gamestate;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;

public interface GameState {

    GameState update(Keyboard keyboard);
    void draw(Camera camera);

}
