package no.taardal.pixelcave.camera;

import no.taardal.pixelcave.actor.Player;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.game.Game;
import no.taardal.pixelcave.sprite.Sprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

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
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

        centerOnPlayerRequired = true;
        left = (int) (width * (30 / 100.0f));
        right = (int) (width * (70 / 100.0f));
        top = (int) (height * (30 / 100.0f));
        bottom = (int) (height * (70 / 100.0f));
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

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void drawSprite(Sprite sprite, int x, int y) {
        boolean flipHorizontally = false;
        boolean flipVertically = false;
        drawSprite(sprite, x, y, flipHorizontally, flipVertically);
    }

    public void drawSpriteFlippedHorizontally(Sprite sprite, int x, int y) {
        boolean flipHorizontally = true;
        boolean flipVertically = false;
        drawSprite(sprite, x, y, flipHorizontally, flipVertically);
    }

    private void drawSprite(Sprite sprite, int x, int y, boolean flipHorizontally, boolean flipVertically) {
        x -= this.x;
        y -= this.y;
        for (int spriteY = y < 0 ? Math.abs(y) : 0; spriteY < sprite.getHeight(); spriteY++) {
            int pixelY = y + spriteY;
            int spritePixelY = spriteY;
            if (flipVertically) {
                spritePixelY = (sprite.getHeight() - 1) - spriteY;
            }
            if (pixelY < 0) {
                continue;
            }
            if (pixelY >= height) {
                break;
            }
            for (int spriteX = x < 0 ? Math.abs(x) : 0; spriteX < sprite.getWidth(); spriteX++) {
                int pixelX = x + spriteX;
                int spritePixelX = spriteX;
                if (flipHorizontally) {
                    spritePixelX = (sprite.getWidth() - 1) - spriteX;
                }
                if (pixelX < 0) {
                    continue;
                }
                if (pixelX >= width) {
                    break;
                }
                int color = sprite.getPixels()[spritePixelX + spritePixelY * sprite.getWidth()];
                if (!isTransparent(color)) {
                    pixels[pixelX + pixelY * width] = color;
                }
            }
        }
    }

    private boolean isTransparent(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        return alpha == 0;
    }

}
