package no.taardal.pixelcave.camera;


import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Camera extends Rectangle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Camera.class);
    private static final float TWEEN = 0.03f;

    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;
    private Direction direction;
    private int offsetX;
    private int offsetY;
    private int previousOffsetX;
    private int previousOffsetY;

    public Camera(int width, int height) {
        super(width, height);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = bufferedImage.createGraphics();
        direction = Direction.NO_DIRECTION;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getPreviousOffsetX() {
        return previousOffsetX;
    }

    public int getPreviousOffsetY() {
        return previousOffsetY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void update(int x, int y) {
        previousOffsetX = offsetX;
        previousOffsetY = offsetY;

        float deltaX = ((x - Game.GAME_WIDTH / 2) - offsetX);
        float deltaY = ((y - Game.GAME_HEIGHT / 2) - offsetY);

        offsetX += deltaX;
        offsetY += deltaY;

        if (deltaX > 1) {
            direction = Direction.EAST;
        } else if (deltaX < 0) {
            direction = Direction.WEST;
        } else {
            direction = Direction.NO_DIRECTION;
        }

        if (offsetX <= 0) {
            offsetX = 0;
            direction = Direction.NO_DIRECTION;
        }
        if (offsetY < 0) {
            offsetY = 0;
        }
    }

    public void clear() {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public void drawImage(BufferedImage bufferedImage, int x, int y) {
        x -= offsetX;
        y -= offsetY;
        graphics2D.drawImage(bufferedImage, x, y, null);
    }

    public void drawImage(BufferedImage bufferedImage, int destinationX1, int destinationX2, int destinationY1, int destinationY2, int sourceX1, int sourceX2, int sourceY1, int sourceY2) {
        graphics2D.drawImage(bufferedImage,
                destinationX1, destinationY1, destinationX2, destinationY2,
                sourceX1, sourceY1, sourceX2, sourceY2,
                null);
    }

    public void drawImageFlippedHorizontally(BufferedImage bufferedImage, int x, int y) {
        x -= offsetX;
        y -= offsetY;

        int sourceX1 = 0;
        int sourceX2 = bufferedImage.getWidth();
        int sourceY1 = 0;
        int sourceY2 = bufferedImage.getHeight();
        int destinationX1 = x;
        int destinationX2 = x + bufferedImage.getWidth();
        int destinationY1 = y;
        int destinationY2 = y + bufferedImage.getHeight();

        drawImage(bufferedImage,
                destinationX1, destinationX2, destinationY1, destinationY2,
                sourceX2, sourceX1, sourceY1, sourceY2);
    }

    public void drawString(String text, int x, int y, Font font, Color color) {
        graphics2D.setColor(color);
        graphics2D.setFont(font);
        graphics2D.drawString(text, x, y);
    }

    public void drawCircle(int x, int y, int diameter, Color color) {
        graphics2D.setColor(color);
        graphics2D.fillOval(x, y, diameter, diameter);
    }

    public void drawRectangle(int x, int y, int width, int height, Color color) {
        x -= offsetX;
        y -= offsetY;
        graphics2D.setColor(color);
        graphics2D.drawRect(x, y, width, height);
    }

    public void drawRectangle(Rectangle rectangle, Color color) {
        drawRectangle(rectangle.x, rectangle.y, (int) rectangle.getWidth(), (int) rectangle.getHeight(), color);
    }

    public void drawRectangle(Rectangle2D rectangle, Color color) {
        drawRectangle((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), color);
    }

    public void drawBounds(Bounds rectangle, Color color) {
        drawRectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), color);
    }

}
