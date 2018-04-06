package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.state.KnightIdleState;
import no.taardal.pixelcave.vector.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Knight extends Actor implements Player {

    private static final Logger LOGGER = LoggerFactory.getLogger(Knight.class);

    public enum Theme {

        BLACK,
        BLUE,
        ELDER,
        GOLD,
        GREY,
        HOLY,
        RED,
        WHITE,
        YELLOW,
        YOUNG;

    }

    public Knight(SpriteSheet spriteSheet, Direction direction, Vector2f velocity, Vector2f position) {
        super(spriteSheet, direction, velocity, position);
        stateMachine.onPushState(new KnightIdleState(this, stateMachine));
        width = 18;
        height = 29;
        movementSpeed = 100;
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera);
        camera.drawRectangle(position.getX(), position.getY(), getWidth(), getHeight(), Color.CYAN);
    }

    @Override
    Map<Animation.Type, Animation> createAnimations() {
        Map<Animation.Type, Animation> animations = new HashMap<>();
        animations.put(Animation.Type.IDLE, getIdleAnimation());
        animations.put(Animation.Type.RUN, getRunAnimation());
        animations.put(Animation.Type.JUMP, getJumpAnimation());
        animations.put(Animation.Type.FALL, getFallAnimation());
        animations.put(Animation.Type.LAND, getLandAnimation());
        return animations;
    }

    @Override
    boolean isFlipped() {
        return direction == Direction.LEFT;
    }

    private Animation getIdleAnimation() {
        BufferedImage[] sprites = new BufferedImage[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][0];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(10);
        return animation;
    }

    private Animation getRunAnimation() {
        BufferedImage[] sprites = new BufferedImage[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][8];
        }
        return new Animation(sprites);
    }

    private Animation getJumpAnimation() {
        BufferedImage[] sprites = new BufferedImage[7];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getFallAnimation() {
        BufferedImage[] sprites = {spriteSheet.getSprites()[6][10]};
        Animation animation = new Animation(sprites);
        animation.setIndefinite(true);
        return animation;
    }

    private Animation getLandAnimation() {
        BufferedImage[] sprites = new BufferedImage[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i + 7][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

    public static class Builder {

        private SpriteSheet spriteSheet;
        private Direction direction;
        private Vector2f position;
        private Vector2f velocity;

        public Builder setSpriteSheet(SpriteSheet spriteSheet) {
            this.spriteSheet = spriteSheet;
            return this;
        }

        public Builder setDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder setVelocity(Vector2f velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder setPosition(Vector2f position) {
            this.position = position;
            return this;
        }

        public Builder setPosition(float x, float y) {
            this.position = new Vector2f(x, y);
            return this;
        }

        public Builder setX(float x) {
            if (position != null) {
                position.setX(x);
            } else {
                position = new Vector2f(x, 0);
            }
            return this;
        }

        public Builder setY(float y) {
            if (position != null) {
                position.setY(y);
            } else {
                position = new Vector2f(0, y);
            }
            return this;
        }

        public Knight build() {
            return new Knight(spriteSheet, direction, velocity, position);
        }

    }
}

