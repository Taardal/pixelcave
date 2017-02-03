package no.taardal.blossom.gamestate;

import no.taardal.blossom.input.KeyEventType;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.level.TestLevel;
import no.taardal.blossom.view.Camera;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuGameState implements GameState {

    private static final int START = 1;
    private static final int EXIT = 2;

    private List<Level> levels;
    private int selectedMenuItem;

    public MenuGameState() {
        levels = new ArrayList<>();
        levels.add(new TestLevel());
    }

    @Override
    public GameState onKeyEvent(KeyEventType keyEventType, KeyEvent keyEvent) {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Camera camera) {

    }

}
