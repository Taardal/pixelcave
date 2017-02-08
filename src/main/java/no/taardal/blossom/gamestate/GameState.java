package no.taardal.blossom.gamestate;

import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.view.Camera;

interface GameState {

    GameState update(Keyboard keyboard);
    void draw(Camera camera);

}
