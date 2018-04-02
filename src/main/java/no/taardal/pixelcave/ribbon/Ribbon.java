package no.taardal.pixelcave.ribbon;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class Ribbon {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ribbon.class);

    private BufferedImage bufferedImage;
    private int bufferedImageX;
    private int bufferedImageY;
    private int speedX;
    private int speedY;

    public Ribbon(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void update(Direction cameraDirection) {
        Direction direction = getDirection(cameraDirection);
        if (direction == Direction.RIGHT) {
            bufferedImageX = (bufferedImageX + speedX) % bufferedImage.getWidth();
        } else if (direction == Direction.LEFT) {
            bufferedImageX = (bufferedImageX - speedX) % bufferedImage.getWidth();
        }
        if (direction == Direction.UP) {
            bufferedImageY -= speedY;
            if (bufferedImageY < 0) {
                bufferedImageY = 0;
            }
        } else if (direction == Direction.DOWN) {
            bufferedImageY += speedY;
            if (bufferedImageY + Game.GAME_HEIGHT > bufferedImage.getHeight()) {
                bufferedImageY = bufferedImage.getHeight() - Game.GAME_HEIGHT;
            }
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

    /*
    Killer Game Programming book, page 313-320
     */
    public void draw(Camera camera) {
        int destinationY1 = 0;
        int destinationY2 = (int) camera.getHeight();
        int sourceY1 = bufferedImageY;
        int sourceY2 = bufferedImageY + (int) camera.getHeight();

        if (bufferedImageX == 0) {
            int destinationX1 = 0;
            int destinationX2 = (int) camera.getWidth();
            int sourceX1 = 0;
            int sourceX2 = (int) camera.getWidth();

            camera.drawImage(bufferedImage, destinationX1, destinationX2, destinationY1, destinationY2, sourceX1, sourceX2, sourceY1, sourceY2);

        } else if (bufferedImageX > 0 && bufferedImageX < (int) camera.getWidth()) {
            int tailDestinationX1 = 0;
            int tailDestinationX2 = bufferedImageX;
            int tailSourceX1 = bufferedImage.getWidth() - bufferedImageX;
            int tailSourceX2 = bufferedImage.getWidth();
            camera.drawImage(bufferedImage, tailDestinationX1, tailDestinationX2, destinationY1, destinationY2, tailSourceX1, tailSourceX2, sourceY1, sourceY2);

            int headDestinationX1 = bufferedImageX;
            int headDestinationX2 = (int) camera.getWidth();
            int headSourceX1 = 0;
            int headSourceX2 = (int) camera.getWidth() - bufferedImageX;
            camera.drawImage(bufferedImage, headDestinationX1, headDestinationX2, destinationY1, destinationY2, headSourceX1, headSourceX2, sourceY1, sourceY2);

        } else if (bufferedImageX >= (int) camera.getWidth()) {
            int destinationX1 = 0;
            int destinationX2 = (int) camera.getWidth();
            int sourceX1 = bufferedImage.getWidth() - bufferedImageX;
            int sourceX2 = bufferedImage.getWidth() - bufferedImageX + (int) camera.getWidth();
            camera.drawImage(bufferedImage, destinationX1, destinationX2, destinationY1, destinationY2, sourceX1, sourceX2, sourceY1, sourceY2);

        } else if ((bufferedImageX < 0) && (bufferedImageX >= (int) camera.getWidth() - bufferedImage.getWidth())) {
            int destinationX1 = 0;
            int destinationX2 = (int) camera.getWidth();
            int sourceX1 = -bufferedImageX;
            int sourceX2 = (int) camera.getWidth() - bufferedImageX;
            camera.drawImage(bufferedImage, destinationX1, destinationX2, destinationY1, destinationY2, sourceX1, sourceX2, sourceY1, sourceY2);

        } else if (bufferedImageX < (int) camera.getWidth() - bufferedImage.getWidth()) {
            int tailDestinationX1 = 0;
            int tailDestinationX2 = bufferedImage.getWidth() + bufferedImageX;
            int tailSourceX1 = -bufferedImageX;
            int tailSourceX2 = bufferedImage.getWidth();
            camera.drawImage(bufferedImage, tailDestinationX1, tailDestinationX2, destinationY1, destinationY2, tailSourceX1, tailSourceX2, sourceY1, sourceY2);

            int headDestinationX1 = bufferedImage.getWidth() + bufferedImageX;
            int headDestinationX2 = (int) camera.getWidth();
            int headSourceX1 = 0;
            int headSourceX2 = (int) camera.getWidth() - bufferedImage.getWidth() - bufferedImageX;
            camera.drawImage(bufferedImage, headDestinationX1, headDestinationX2, destinationY1, destinationY2, headSourceX1, headSourceX2, sourceY1, sourceY2);
        }
    }

}
