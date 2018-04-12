package no.taardal.pixelcave.tile;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.sprite.Sprite;

import java.awt.image.BufferedImage;

public class Tile {

    private Sprite sprite;
    private boolean slope;
    private Direction direction;

    public Tile(BufferedImage sprite) {
        this.sprite = new Sprite(sprite);
        direction = Direction.NO_DIRECTION;
        slope = isSlopeTile();
    }

    @Override
    public String toString() {
        return "Tile{" +
                "slope=" + slope +
                ", direction=" + direction +
                '}';
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public boolean isSlope() {
        return slope;
    }

    public Direction getDirection() {
        return direction;
    }

    public void draw(int x, int y, Camera camera) {
        camera.drawSprite(sprite, x, y);
    }

    private boolean isSlopeTile() {
        int[] spritePixels = sprite.getPixels();
        int leftFloorY = 0;
        int rightFloorY = 0;
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                int pixel = spritePixels[x + y * getWidth()];
                if (x == 0) {
                    if (isTransparent(pixel)) {
                        leftFloorY = y;
                    }
                }
                if (x == getWidth() - 1) {
                    if (isTransparent(pixel)) {
                        rightFloorY = y;
                    }
                }
            }
        }
        if (leftFloorY != rightFloorY) {
            if (leftFloorY < rightFloorY) {
                direction = Direction.LEFT;
            }
            if (leftFloorY > rightFloorY) {
                direction = Direction.RIGHT;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isTransparent(int pixel) {
        return (pixel >> 24) == 0x00;
    }

}
