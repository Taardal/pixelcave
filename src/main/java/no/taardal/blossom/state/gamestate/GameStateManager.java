package no.taardal.blossom.state.gamestate;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GameStateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStateManager.class);

    private GameState gameState;

    public GameStateManager(List<Level> levels) {
        gameState = new PlayGameState(levels.get(0));
    }

    public void update(Keyboard keyboard, Camera camera) {
        GameState gameState = this.gameState.update(keyboard, camera);
        if (gameState != null) {
            LOGGER.debug("Changing game state to [{}]", gameState);
            this.gameState = gameState;
        }
    }

    public void draw(Camera camera) {
        gameState.draw(camera);
    }

}
