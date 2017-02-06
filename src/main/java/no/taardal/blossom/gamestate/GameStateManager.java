package no.taardal.blossom.gamestate;

import no.taardal.blossom.input.KeyEventType;
import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.listener.ExitListener;
import no.taardal.blossom.view.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;

public class GameStateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStateManager.class);

    private GameState gameState;

    public GameStateManager(ExitListener exitListener) {
        gameState = new MenuGameState(exitListener);
    }

    public void onKeyEvent(KeyEventType keyEventType, KeyEvent keyEvent) {
        GameState gameState = this.gameState.onKeyEvent(keyEventType, keyEvent);
        if (gameState != null) {
            LOGGER.debug("Changing game state to [{}]", gameState);
            this.gameState = gameState;
        }
    }

    public void update(Keyboard keyboard) {
        GameState gameState = this.gameState.update(keyboard);
        if (gameState != null) {
            LOGGER.debug("Changing game state to [{}]", gameState);
            this.gameState = gameState;
        }
    }

    public void draw(Camera camera) {
        gameState.draw(camera);
    }

}
