package no.taardal.blossom.gamestate;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;

public interface GameStateManager {
    void update(Keyboard keyboard);
    void draw(Camera camera);
}
