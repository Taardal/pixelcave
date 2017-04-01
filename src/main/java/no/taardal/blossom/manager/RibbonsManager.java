package no.taardal.blossom.manager;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.ribbon.Ribbon;

import java.util.List;

public class RibbonsManager implements Manager<Ribbon> {

    private static final int HORIZONTAL_SPEED = 5;
    private static final int VERTICAL_SPEED = 1;
    private static final double SPEED_FACTOR = 0.16;
    private static final double SPEED_FACTOR_INCREMENT = 0.19;

    private List<Ribbon> ribbons;

    public RibbonsManager(List<Ribbon> ribbons) {
        this.ribbons = ribbons;
        setRibbonSpeeds(ribbons);
    }

    @Override
    public void update(Keyboard keyboard) {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).update();
        }
    }

    @Override
    public void draw(Camera camera) {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).draw(camera);
        }
    }

    private void setRibbonSpeeds(List<Ribbon> ribbons) {
        double speedFactor = SPEED_FACTOR;
        for (Ribbon ribbon : ribbons) {
            ribbon.setSpeedX((int) (speedFactor * HORIZONTAL_SPEED));
            ribbon.setSpeedY(VERTICAL_SPEED);
            speedFactor += SPEED_FACTOR_INCREMENT;
        }
    }

    public void moveUp() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingUp(true);
            ribbons.get(i).setMovingDown(false);
        }
    }

    public void moveRight() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingRight(true);
            ribbons.get(i).setMovingLeft(false);
        }
    }

    public void moveLeft() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingLeft(true);
            ribbons.get(i).setMovingRight(false);
        }
    }

    public void moveDown() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingDown(true);
            ribbons.get(i).setMovingUp(false);
        }
    }

    public void stopMovingHorizontally() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingRight(false);
            ribbons.get(i).setMovingLeft(false);
        }
    }

    public void stopMovingVertically() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingUp(false);
            ribbons.get(i).setMovingDown(false);
        }
    }

}
