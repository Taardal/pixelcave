package no.taardal.blossom.ribbon;

import no.taardal.blossom.camera.Camera;

import java.util.Collections;
import java.util.List;

public class Ribbon {

    private List<Background> backgrounds;
    private double x;
    private double y;

    public Ribbon(List<Background> backgrounds) {
        this.backgrounds = backgrounds;
        Collections.reverse(this.backgrounds);
    }

    public List<Background> getBackgrounds() {
        return backgrounds;
    }

    public void update(double x, double y) {
        double velocityX = 0;
        double velocityY = 0;
        for (Background background : backgrounds) {
            background.update(x, y, velocityX, velocityY);
            velocityX += 0.2;
            velocityY += 0.1;
        }
    }

    public void draw(Camera camera) {
        for (int i = 0; i < backgrounds.size(); i++) {
            backgrounds.get(i).draw(camera);
        }
    }

}
