package no.taardal.pixelcave.camera;

import no.taardal.pixelcave.actor.Player;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.game.Game;
import no.taardal.pixelcave.sprite.Sprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

public class Camera {

    private static final Logger LOGGER = LoggerFactory.getLogger(Camera.class);

    private BufferedImage bufferedImage;
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

        pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
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
>>>>>>> c76671d212277562266853b4bdeb6d840ddb18e2
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

    public void drawSprite(Sprite sprite, float x, float y) {
        int cameraX = ((int) x) - this.x;
        int cameraY = ((int) y) - this.y;
        for (int spriteY = 0; spriteY < sprite.getHeight(); spriteY++) {
            int pixelY = spriteY + cameraY;
            if (pixelY < 0) {
                continue;
            }
            if (pixelY >= height) {
                break;
            }
            for (int spriteX = 0; spriteX < sprite.getWidth(); spriteX++) {
                int pixelX = spriteX + cameraX;
                if (pixelX < 0) {
                    continue;
                }
                if (pixelX >= width) {
                    break;
                }
                pixels[pixelX + pixelY * width] = sprite.getPixels()[spriteX + spriteY * sprite.getWidth()];
            }
        }
    }

    public void drawSprite(Sprite sprite, int positionX, int positionY, int flip) {
        positionX -= x;
        positionY -= y;
        for (int y = 0; y < sprite.getHeight(); y++) {
            int absoluteY = y + positionY;
            int spriteY = y;
            if (flip == 2 || flip == 3) {
                spriteY = (sprite.getHeight() - 1) - y;
            }
            for (int x = 0; x < sprite.getWidth(); x++) {
                int absoluteX = x + positionX;
                int spriteX = x;
                if (flip == 1 || flip == 3) {
                    spriteX = (sprite.getWidth() - 1) - x;
                }
                if (absoluteX < -sprite.getWidth() || absoluteX >= width || absoluteY < -sprite.getHeight() || absoluteY >= height) {
                    break;
                }
                if (absoluteX < 0) {
                    absoluteX = 0;
                }
                if (absoluteY < 0) {
                    absoluteY = 0;
                }
                int spritePixel = sprite.getPixels()[spriteX + spriteY * sprite.getWidth()];
                if (((spritePixel >> 24) & 0xFF) != 0) {
                    pixels[absoluteX + absoluteY * width] = spritePixel;
                }
            }
        }
    }

    public void drawImage(BufferedImage bufferedImage, float x, float y, boolean flip) {
        if (flip) {
            drawImageFlippedHorizontally(bufferedImage, x, y);
        } else {
            drawImage(bufferedImage, x, y);
        }
    }

    public void drawImage(BufferedImage bufferedImage, float x, float y) {

    }

    public void drawImage(BufferedImage bufferedImage, int destinationX1, int destinationX2, int destinationY1, int destinationY2, int sourceX1, int sourceX2, int sourceY1, int sourceY2) {

    }

    public void drawRectangle(float x, float y, int width, int height, Color color) {

    }

    public void drawString(String text, int x, int y, Font font, Color color) {

    }

    public void drawCircle(int x, int y, int diameter, Color color) {

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
