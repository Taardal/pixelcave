package no.taardal.blossom.state;

import no.taardal.blossom.view.Camera;

public class GameStateManager {

    private static final MenuState MENU_STATE = new MenuState();
    private static final PlayState PLAY_STATE = new PlayState();

    private State state;

    public GameStateManager() {
        state = PLAY_STATE;
    }

    public State getState() {
        return state;
    }

    public void update() {
        state.update();
    }

    public void draw(Camera camera) {
        state.draw(camera);
    }

}
