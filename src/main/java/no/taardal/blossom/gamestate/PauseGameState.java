package no.taardal.blossom.gamestate;

import no.taardal.blossom.input.KeyEventType;
import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.view.Camera;

import java.awt.event.KeyEvent;

class PauseGameState implements GameState {

    PauseGameState(Level level) {
    }

    @Override
    public GameState update(Keyboard keyboard) {
        return null;
    }

    @Override
    public void draw(Camera camera) {

    }

}
