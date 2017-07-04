package no.taardal.blossom.state.gamestate;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.level.Level;

public class PauseGameState implements GameState {

    PauseGameState(Level level) {
    }

    @Override
    public GameState update(double timeSinceLastUpdate, Keyboard keyboard, Camera camera) {
        return null;
    }

    @Override
    public void draw(Camera camera) {

    }

}
