package no.taardal.blossom.manager;

import com.google.inject.Inject;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.gamestate.GameState;
import no.taardal.blossom.gamestate.PlayGameState;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GameStateManager implements Manager<GameState> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStateManager.class);

    private GameState gameState;

    @Inject
    public GameStateManager(List<Level> levels) {
        gameState = new PlayGameState(levels.get(0));
    }

    @Override
    public void update(Keyboard keyboard) {
        GameState gameState = this.gameState.update(keyboard);
        if (gameState != null) {
            LOGGER.debug("Changing game state to [{}]", gameState);
            this.gameState = gameState;
        }
    }

    @Override
    public void draw(Camera camera) {
        gameState.draw(camera);
    }

}
