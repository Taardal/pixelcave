package no.taardal.blossom.gamestate;

import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.camera.Camera;

interface GameState {

    GameState update(Keyboard keyboard);
    void draw(Camera camera);

}
