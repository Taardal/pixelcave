package no.taardal.pixelcave.state.game;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayGameState implements GameState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGameState.class);

    private Level level;

    public PlayGameState(Level level) {
        this.level = level;
    }

    @Override
    public GameState handleInput(Keyboard keyboard) {
        level.handleInput(keyboard);
        return null;
    }

    @Override
    public GameState update(double secondsSinceLastUpdate, Camera camera) {
        level.update(secondsSinceLastUpdate, camera);
        return null;
    }

    @Override
    public void draw(Camera camera) {
        level.draw(camera);
    }

}
