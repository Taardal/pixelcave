package no.taardal.blossom.sprite;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.camera.Camera;

public class Animation {

    private static final int DEFAULT_UPDATES_PER_FRAME = 5;

    private Sprite[] sprites;
    private Sprite sprite;
    private int frame;
    private int updatesPerFrame;
    private int updatesSinceLastFrame;
    private boolean indefinite;
    private boolean finished;

    private Animation() {
        updatesPerFrame = DEFAULT_UPDATES_PER_FRAME;
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

    public int getFrame() {
        return frame;
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
            if (updatesSinceLastFrame >= updatesPerFrame) {
                if (frame > sprites.length - 1) {
                    if (indefinite) {
                        frame = 0;
                    } else {
                        finished = true;
                    }
                } else {
                    sprite = sprites[frame];
                    frame++;
                }
                updatesSinceLastFrame = 0;
            }
            updatesSinceLastFrame++;
        }
    }

    public void reset() {
        finished = false;
        sprite = sprites[0];
        frame = 0;
        updatesSinceLastFrame = 0;
    }

    public void draw(Actor actor, Camera camera) {
        sprite.draw(actor.getX(), actor.getY(), camera);
    }

    public void drawFlippedHorizontally(Actor actor, Camera camera) {
        sprite.drawFlippedHorizontally(actor.getX(), actor.getY(), camera);
    }
}
