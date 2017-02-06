package no.taardal.blossom.gamestate;

import no.taardal.blossom.input.KeyEventType;
import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.view.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_ESCAPE;

class PlayGameState implements GameState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGameState.class);

    private Level level;

    PlayGameState(Level level) {
        this.level = level;
    }

    @Override
    public GameState onKeyEvent(KeyEventType keyEventType, KeyEvent keyEvent) {
        if (keyEventType == KeyEventType.RELEASED && keyEvent.getKeyCode() == VK_ESCAPE) {
            LOGGER.debug("Escape pressed. Entering pause state.");
            return new PauseGameState(level);
        } else {
            return null;
        }
    }

    @Override
    public GameState update(Keyboard keyboard) {
        if (keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            LOGGER.debug("Escape pressed. Entering pause state.");
            return new PauseGameState(level);
        } else {
            return null;
        }
    }

    @Override
    public void draw(Camera camera) {
        level.draw(camera);
    }

}
