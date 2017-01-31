package no.taardal.blossom.state;

import no.taardal.blossom.level.Level;
import no.taardal.blossom.level.TestLevel;
import no.taardal.blossom.view.Camera;

import java.util.ArrayList;
import java.util.List;

public class PlayState implements State {

    private List<Level> levels;
    private int currentLevel;

    public PlayState() {
        levels = new ArrayList<>();
        levels.add(new TestLevel());
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void update() {
        levels.get(currentLevel).update();
    }

    @Override
    public void draw(Camera camera) {
        levels.get(currentLevel).draw(camera);
    }

}
