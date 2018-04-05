package no.taardal.pixelcave.ribbon;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class Ribbon {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ribbon.class);

    private BufferedImage bufferedImage;
    private float x;
    private float y;
    private float speedX;
    private float speedY;

    public Ribbon(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void update(Direction cameraDirection) {
        Direction direction = getDirection(cameraDirection);
        if (direction == Direction.RIGHT) {
            x = (x + speedX) % bufferedImage.getWidth();
        } else if (direction == Direction.LEFT) {
            x = (x - speedX) % bufferedImage.getWidth();
        }
    }

    public void draw(Camera camera) {
        int destinationY1 = 0;
        int destinationY2 = camera.getHeight();
        int sourceY1 = (int) y;
        int sourceY2 = ((int) y) + camera.getHeight();

        if (x == 0) {
            int destinationX1 = 0;
            int destinationX2 = camera.getWidth();
            int sourceX1 = 0;
            int sourceX2 = camera.getWidth();

            camera.drawImage(bufferedImage, destinationX1, destinationX2, destinationY1, destinationY2, sourceX1, sourceX2, sourceY1, sourceY2);

        } else if (x > 0 && x < camera.getWidth()) {
            int tailDestinationX1 = 0;
            int tailDestinationX2 = (int) x;
            int tailSourceX1 = bufferedImage.getWidth() - (int) x;
            int tailSourceX2 = bufferedImage.getWidth();
            camera.drawImage(bufferedImage, tailDestinationX1, tailDestinationX2, destinationY1, destinationY2, tailSourceX1, tailSourceX2, sourceY1, sourceY2);

            int headDestinationX1 = (int) x;
            int headDestinationX2 = camera.getWidth();
            int headSourceX1 = 0;
            int headSourceX2 = camera.getWidth() - (int) x;
            camera.drawImage(bufferedImage, headDestinationX1, headDestinationX2, destinationY1, destinationY2, headSourceX1, headSourceX2, sourceY1, sourceY2);

        } else if (x >= camera.getWidth()) {
            int destinationX1 = 0;
            int destinationX2 = camera.getWidth();
            int sourceX1 = bufferedImage.getWidth() - (int) x;
            int sourceX2 = bufferedImage.getWidth() - ((int) x) + camera.getWidth();
            camera.drawImage(bufferedImage, destinationX1, destinationX2, destinationY1, destinationY2, sourceX1, sourceX2, sourceY1, sourceY2);

        } else if ((x < 0) && (x >= camera.getWidth() - bufferedImage.getWidth())) {
            int destinationX1 = 0;
            int destinationX2 = camera.getWidth();
            int sourceX1 = (int) -x;
            int sourceX2 = camera.getWidth() - (int) x;
            camera.drawImage(bufferedImage, destinationX1, destinationX2, destinationY1, destinationY2, sourceX1, sourceX2, sourceY1, sourceY2);

        } else if (x < camera.getWidth() - bufferedImage.getWidth()) {
            int tailDestinationX1 = 0;
            int tailDestinationX2 = bufferedImage.getWidth() + (int) x;
            int tailSourceX1 = (int) -x;
            int tailSourceX2 = bufferedImage.getWidth();
            camera.drawImage(bufferedImage, tailDestinationX1, tailDestinationX2, destinationY1, destinationY2, tailSourceX1, tailSourceX2, sourceY1, sourceY2);

            int headDestinationX1 = bufferedImage.getWidth() + (int) x;
            int headDestinationX2 = camera.getWidth();
            int headSourceX1 = 0;
            int headSourceX2 = camera.getWidth() - bufferedImage.getWidth() - (int) x;
            camera.drawImage(bufferedImage, headDestinationX1, headDestinationX2, destinationY1, destinationY2, headSourceX1, headSourceX2, sourceY1, sourceY2);
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
