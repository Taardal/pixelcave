package no.taardal.blossom.gamestate;

import no.taardal.blossom.input.KeyEventType;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.view.Camera;

import java.awt.event.KeyEvent;

public class PauseGameState implements GameState {
    public PauseGameState(Level level) {
    }

    @Override
    public GameState onKeyEvent(KeyEventType keyEventType, KeyEvent keyEvent) {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Camera camera) {

    }
}
