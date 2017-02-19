package no.taardal.blossom.gamestate;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.level.Level;

public class PauseGameState implements GameState {

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
