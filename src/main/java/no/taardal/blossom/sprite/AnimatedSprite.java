package no.taardal.blossom.sprite;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;

import java.awt.image.BufferedImage;

public class AnimatedSprite {

    private SpriteSheet sheet;
    private Sprite sprite;
    private int width;
    private int height;
    private int frame = 0;
    private int rate = 10;
    private int length = -1;
    private int time = 0;
    private int flip = 0;
    private int animationSize = 0;

    private Sprite[] sprites;

    public AnimatedSprite(SpriteSheet spriteSheet, int width, int height, int length) {
        this.sheet = spriteSheet;
        this.width = width;
        this.height = height;
        this.length = length;
        sprite = spriteSheet.getSprites()[0];
    }

    public AnimatedSprite(Sprite[] sprites, int width, int height) {
        this.width = width;
        this.height = height;
        this.sprites = sprites;
        sprite = sprites[0];
        length = 4;
    }

    public void update() {
        time++;
        if (time % rate == 0) {
            if (frame >= length - 1) {
                frame = length - 1;
                flip = 1;
            } else if (frame <= 1) {
                frame = 1;
                flip = 0;
            }
            if (sprites != null) {
                sprite = sprites[frame];
            } else {
                sprite = sheet.getSprites()[frame];
            }
            if (flip == 1) {
                frame --;
            } else {
                frame++;
            }
        }
    }

    public void draw(int x, int y, Direction direction, Camera camera) {
        if (direction == Direction.EAST) {
            BufferedImage bufferedImage = sprite.getBufferedImage();

            int destinationX1 = x;
            int destinationX2 = x + getWidth();
            int destinationY1 = y;
            int destinationY2 = y + getHeight();
            int sourceX1 = 0;
            int sourceX2 = bufferedImage.getWidth();
            int sourceY1 = 0;
            int sourceY2 = bufferedImage.getHeight();

            camera.drawImageFlip(bufferedImage, destinationX1, destinationX2, destinationY1, destinationY2, sourceX1, sourceX2, sourceY1, sourceY2);
        } else {
            camera.drawImage(sprite.getBufferedImage(), x, y);
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setFrameRates(int frames) {
        rate = frames;
    }

    public void setFrame(int frame) {
        if (sprites != null) {
            sprite = sprites[frame];
        } else {
            sprite = sheet.getSprites()[frame];
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
