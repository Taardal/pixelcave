package no.taardal.blossom.gamestate;

import no.taardal.blossom.input.KeyEventType;
import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.view.Camera;

import java.awt.event.KeyEvent;

interface GameState {

    GameState onKeyEvent(KeyEventType keyEventType, KeyEvent keyEvent);
    GameState update(Keyboard keyboard);
    void draw(Camera camera);

}
