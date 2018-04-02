package no.taardal.pixelcave.animation;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.camera.Camera;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

public class Animation {

    private static final int DEFAULT_UPDATES_PER_FRAME = 5;

    public enum Type {
        IDLE,
        FALL,
        LAND,
        JUMP,
        RUN,
        TUMBLE,
        ATTACK,
        ATTACK_MID_AIR,
        ATTACK_WHILE_CROUCHED,
        DEFEND,
        DEFEND_WHILE_CROUCHED,
        CROUCH,
        HURT,
        DEAD
    }

    private BufferedImage[] sprites;
    private BufferedImage sprite;
    private int frame;
    private int updatesPerFrame;
    private int updatesSinceLastFrame;
    private int width;
    private int height;
    private boolean indefinite;
    private boolean finished;

    private Animation() {
        updatesPerFrame = DEFAULT_UPDATES_PER_FRAME;
        indefinite = true;
    }

    public Animation(BufferedImage[] sprites) {
        this();
        this.sprites = sprites;
        sprite = sprites[0];
        for (int i = 0; i < sprites.length; i++) {
            width = sprites[i].getWidth() > width ? sprites[i].getWidth() : width;
            height = sprites[i].getHeight() > height ? sprites[i].getHeight() : height;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
            setIndefinite(false);
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

        float actorY = actor.getY();
        float actorBottomY = actorY + actor.getHeight();
        float y = actorBottomY - sprite.getHeight();

        float actorX = actor.getX();
        float actorRightX = actorX + actor.getWidth();
        float x = actorRightX - sprite.getWidth();

        camera.drawImagez(sprite, actorX, y);
    }

    public void drawFlippedHorizontally(Actor actor, Camera camera) {
        float actorY = (float) actor.getY();
        float actorBottomY = actorY + actor.getHeight();
        float y = actorBottomY - sprite.getHeight();

        float actorX = (float) actor.getX();
        float actorRightX = actorX + actor.getWidth();
        float x = actorRightX - sprite.getWidth();

        camera.drawImageFlippedHorizontally(sprite, x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animation animation = (Animation) o;
        return frame == animation.frame &&
                updatesPerFrame == animation.updatesPerFrame &&
                updatesSinceLastFrame == animation.updatesSinceLastFrame &&
                width == animation.width &&
                height == animation.height &&
                indefinite == animation.indefinite &&
                finished == animation.finished &&
                Arrays.equals(sprites, animation.sprites) &&
                Objects.equals(sprite, animation.sprite);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sprite, frame, updatesPerFrame, updatesSinceLastFrame, width, height, indefinite, finished);
        result = 31 * result + Arrays.hashCode(sprites);
        return result;
    }
}
