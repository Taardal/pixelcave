package no.taardal.pixelcave.ribbon;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.sprite.Sprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ribbon {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ribbon.class);

    private Sprite sprite;
    private float x;
    private float speedX;

    public Ribbon(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void update(Camera camera) {
        Direction direction = getDirection(camera.getDirection());
        if (direction == Direction.RIGHT) {
            x = (x + speedX) % sprite.getWidth();
        } else if (direction == Direction.LEFT) {
            x = (x - speedX) % sprite.getWidth();
        }
        if (x + sprite.getWidth() < camera.getX()) {
            x += sprite.getWidth();
        }
        if (x > camera.getX()) {
            x -= sprite.getWidth();
        }
    }

    public void draw(Camera camera) {
        int y = 0;
        camera.drawSprite(sprite, (int) x, y);
        if (x > camera.getX()) {
            camera.drawSprite(sprite, (int) x - sprite.getWidth(), y);
        }
        if (x < camera.getX()) {
            camera.drawSprite(sprite, (int) x + sprite.getWidth(), y);
        }
    }

    private Direction getDirection(Direction cameraDirection) {
        if (cameraDirection == Direction.LEFT) {
            return Direction.RIGHT;
        } else if (cameraDirection == Direction.RIGHT) {
            return Direction.LEFT;
        } else {
            return Direction.NO_DIRECTION;
        }
    }
}
