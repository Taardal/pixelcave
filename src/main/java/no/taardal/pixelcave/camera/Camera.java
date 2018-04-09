package no.taardal.pixelcave.camera;

import no.taardal.pixelcave.actor.Player;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

public class Camera {

    private static final Logger LOGGER = LoggerFactory.getLogger(Camera.class);
    private static final ImageObserver IMAGE_OBSERVER = null;

    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;
    private Direction direction;
    private int width;
    private int height;
    private int x;
    private int y;
    private int left;
    private int right;
    private int top;
    private int bottom;
    private float previousPlayerX;
    private boolean centerOnPlayerRequired;
    private int[] pixels;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        direction = Direction.NO_DIRECTION;
        GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreenDevice = localGraphicsEnvironment.getDefaultScreenDevice();
        GraphicsConfiguration defaultConfiguration = defaultScreenDevice.getDefaultConfiguration();
        bufferedImage = defaultConfiguration.createCompatibleImage(width, height);
        //bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        graphics2D = bufferedImage.createGraphics();
        pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

        centerOnPlayerRequired = true;

        left = (int) (width * (30 / 100.0f));
        right = (int) (width * (70 / 100.0f));
        top = (int) (height * (30 / 100.0f));
        bottom = (int) (height * (70 / 100.0f));
    }

    public void drawImagez(BufferedImage bufferedImage, int[] foo, int xx, int yy) {
        xx -= x;
        yy -= y;
        int bw = bufferedImage.getWidth();
        int bh = bufferedImage.getHeight();
        for (int y = 0; y < bh; y++) {
            int ay = yy + y;
            if (ay < 0) {
                continue;
            }
            if (ay >= height) {
                break;
            }
            for (int x = 0; x < bw; x++) {
                int ax = xx + x;
                if (ax < 0) {
                    continue;
                }
                if (ax >= width) {
                    break;
                }
                int i = foo[x + y * bw];
                if (!isTransparent(i)) {
                    pixels[ax + ay * width] = i;
                }
            }
        }
    }

    private boolean isTransparent(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        return alpha == 0;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public void update(Player player) {
        if (centerOnPlayerRequired) {
            x = ((int) player.getPosition().getX()) - Game.GAME_WIDTH / 2;
            centerOnPlayerRequired = false;
        }
        float playerX = player.getPosition().getX();
        float playerXInCamera = playerX - x;
        if (playerXInCamera < left || (playerXInCamera + player.getWidth()) > right) {
            if (playerXInCamera < left) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }
            x += (int) playerX - (int) previousPlayerX;
        } else {
            direction = Direction.NO_DIRECTION;
        }
        previousPlayerX = playerX;
    }

    public void drawImage(BufferedImage bufferedImage, float x, float y, boolean flipped) {
        if (flipped) {
            drawImageFlippedHorizontally(bufferedImage, x, y);
        } else {
            drawImage(bufferedImage, x, y);
        }
    }

    public void drawImage(BufferedImage bufferedImage, float x, float y) {
        x -= this.x;
        y -= this.y;
        graphics2D.drawImage(bufferedImage, (int) x, (int) y, IMAGE_OBSERVER);
    }

    public void drawImage(BufferedImage bufferedImage, int destinationX1, int destinationX2, int destinationY1, int destinationY2, int sourceX1, int sourceX2, int sourceY1, int sourceY2) {
        graphics2D.drawImage(
                bufferedImage,
                destinationX1,
                destinationY1,
                destinationX2,
                destinationY2,
                sourceX1,
                sourceY1,
                sourceX2,
                sourceY2,
                IMAGE_OBSERVER
        );
    }

    public void drawRectangle(float x, float y, int width, int height, Color color) {
        x -= this.x;
        y -= this.y;
        graphics2D.setColor(color);
        graphics2D.drawRect((int) x, (int) y, width, height);
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

    private void drawImageFlippedHorizontally(BufferedImage bufferedImage, float x, float y) {
        x -= this.x;
        y -= this.y;
        int sourceX1 = 0;
        int sourceX2 = bufferedImage.getWidth();
        int sourceY1 = 0;
        int sourceY2 = bufferedImage.getHeight();
        int destinationX1 = (int) x;
        int destinationX2 = (int) x + bufferedImage.getWidth();
        int destinationY1 = (int) y;
        int destinationY2 = (int) y + bufferedImage.getHeight();
        drawImage(
                bufferedImage,
                destinationX1,
                destinationX2,
                destinationY1,
                destinationY2,
                sourceX2,
                sourceX1,
                sourceY1,
                sourceY2
        );
    }

    public void createGraphics() {
        graphics2D = bufferedImage.createGraphics();
    }
}
