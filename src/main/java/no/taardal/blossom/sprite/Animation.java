package no.taardal.blossom.sprite;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;

import java.awt.image.BufferedImage;

public class Animation {

    private static final int DEFAULT_FRAME_RATE = 10;

    private Sprite[] sprites;
    private Sprite sprite;
    private int frame;
    private int updatesPerFrame;
    private int updatesSinceLastFrame;
    private boolean indefinite;
    private boolean finished;

    private Animation() {
        updatesPerFrame = DEFAULT_FRAME_RATE;
        indefinite = true;
    }

    public Animation(Sprite[] sprites) {
        this();
        this.sprites = sprites;
        sprite = sprites[0];
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public int getUpdatesPerFrame() {
        return updatesPerFrame;
    }

    public void setUpdatesPerFrame(int updatesPerFrame) {
        this.updatesPerFrame = updatesPerFrame;
    }

    public boolean isIndefinite() {
        return indefinite;
    }

    public void setIndefinite(boolean indefinite) {
        this.indefinite = indefinite;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        if (finished) {
            indefinite = false;
        }
    }

    public void update() {
        if (!finished) {
            updatesSinceLastFrame++;
            if (updatesSinceLastFrame >= updatesPerFrame) {
                updatesSinceLastFrame = 0;
                sprite = sprites[frame];
                frame++;
                if (frame > sprites.length - 1) {
                    if (indefinite) {
                        frame = 0;
                    } else {
                        finished = true;
                    }
                }
            }
        }
    }

    public void reset() {
        finished = false;
        frame = 0;
        updatesSinceLastFrame = 0;
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
}
