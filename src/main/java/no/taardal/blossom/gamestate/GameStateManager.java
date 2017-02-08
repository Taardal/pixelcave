package no.taardal.blossom.gamestate;

import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.listener.ExitListener;
import no.taardal.blossom.view.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameStateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStateManager.class);

    private GameState gameState;

    public GameStateManager(ExitListener exitListener) {
        gameState = new MenuGameState(exitListener);
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
