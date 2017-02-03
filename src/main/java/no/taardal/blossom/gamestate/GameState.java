package no.taardal.blossom.gamestate;

import no.taardal.blossom.input.KeyEventType;
import no.taardal.blossom.view.Camera;

import java.awt.event.KeyEvent;

interface GameState {

    GameState onKeyEvent(KeyEventType keyEventType, KeyEvent keyEvent);
    void update();
    void draw(Camera camera);

}
