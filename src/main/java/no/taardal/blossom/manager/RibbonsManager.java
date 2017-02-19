package no.taardal.blossom.manager;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Key;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.ribbon.Ribbon;

import java.util.List;

public class RibbonsManager implements Manager<Ribbon> {

    private static final double SPEED_FACTOR = 0.5;
    private static final double SPEED_FACTOR_INCREMENT = 0.2;

    private List<Ribbon> ribbons;

    public RibbonsManager(List<Ribbon> ribbons) {
        this.ribbons = ribbons;
        setRibbonSpeeds(ribbons);
    }

    @Override
    public void update(Keyboard keyboard) {
        if (keyboard.isPressed(Key.W)) {
            moveDown();
        }
        if (keyboard.isPressed(Key.A)) {
            moveRight();
        }
        if (keyboard.isPressed(Key.S)) {
            moveUp();
        }
        if (keyboard.isPressed(Key.D)) {
            moveLeft();
        }
        if (!keyboard.isPressed(Key.A) && !keyboard.isPressed(Key.D)) {
            stopMovingHorizontally();
        }
        if (!keyboard.isPressed(Key.W) && !keyboard.isPressed(Key.S)) {
            stopMovingVertically();
        }
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
            ribbon.setSpeedX((int) (speedFactor * Camera.SPEED));
            ribbon.setSpeedY(Camera.SPEED);
            speedFactor += SPEED_FACTOR_INCREMENT;
        }
    }

    private void moveUp() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingUp(true);
            ribbons.get(i).setMovingDown(false);
        }
    }

    private void moveRight() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingRight(true);
            ribbons.get(i).setMovingLeft(false);
        }
    }

    private void moveLeft() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingLeft(true);
            ribbons.get(i).setMovingRight(false);
        }
    }

    private void moveDown() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingDown(true);
            ribbons.get(i).setMovingUp(false);
        }
    }

    private void stopMovingHorizontally() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingRight(false);
            ribbons.get(i).setMovingLeft(false);
        }
    }

    private void stopMovingVertically() {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).setMovingUp(false);
            ribbons.get(i).setMovingDown(false);
        }
    }

}
