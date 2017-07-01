package no.taardal.blossom.state.gamestate;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Key;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayGameState implements GameState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGameState.class);

    private Level level;

    public PlayGameState(Level level) {
        this.level = level;
    }

    @Override
    public GameState update(Keyboard keyboard, Camera camera) {
        if (keyboard.isPressed(Key.ESCAPE)) {
            LOGGER.debug("Entering pause state.");
            return new PauseGameState(level);
        } else {
            level.update(keyboard, camera);
            return null;
        }
    }

    @Override
    public void draw(Camera camera) {
        level.draw(camera);
    }

}
