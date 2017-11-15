package no.taardal.pixelcave.ribbon;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;

import java.util.List;

public class RibbonManager {

    private static final int HORIZONTAL_SPEED = 5;
    private static final int VERTICAL_SPEED = 1;
    private static final double BASE_SPEED_FACTOR = 0.2;
    private static final double SPEED_FACTOR_INCREMENT = 0.19;

    private List<Ribbon> ribbons;

    public RibbonManager(List<Ribbon> ribbons) {
        this.ribbons = setRibbonSpeeds(ribbons);
    }

    public void update(Camera camera) {
        Direction direction = getDirection(camera);
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).update(direction);
        }
    }

    public void draw(Camera camera) {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).draw(camera);
        }
    }

    private List<Ribbon> setRibbonSpeeds(List<Ribbon> ribbons) {
        double speedFactor = BASE_SPEED_FACTOR;
        for (Ribbon ribbon : ribbons) {
            ribbon.setSpeedX((int) (speedFactor * HORIZONTAL_SPEED));
            ribbon.setSpeedY(VERTICAL_SPEED);
            speedFactor += SPEED_FACTOR_INCREMENT;
        }
        return ribbons;
    }

    private Direction getDirection(Camera camera) {
        if (camera.getDirection() == Direction.LEFT) {
            return Direction.RIGHT;
        } else if (camera.getDirection() == Direction.RIGHT) {
            return Direction.LEFT;
        } else {
            return Direction.NO_DIRECTION;
        }
    }

}
