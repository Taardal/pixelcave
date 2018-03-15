package no.taardal.pixelcave.spritesheet;

import no.taardal.pixelcave.animation.Animation;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class KnightSpriteSheet extends SpriteSheet {

    private static final int SPRITE_WIDTH = 40;
    private static final int SPRITE_HEIGHT = 40;

    public KnightSpriteSheet(BufferedImage bufferedImage) {
        super(bufferedImage, SPRITE_WIDTH, SPRITE_HEIGHT);
    }

    @Override
    Map<Animation.Type, Animation> createAnimations() {
        Map<Animation.Type, Animation> animations = new HashMap<>();
        animations.put(Animation.Type.IDLE, getIdleAnimation());
        animations.put(Animation.Type.RUN, getRunningAnimation());
        animations.put(Animation.Type.JUMP, getJumpingAnimation());
        return animations;
    }

    private Animation getIdleAnimation() {
        BufferedImage[] sprites = new BufferedImage[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = this.sprites[i][0];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(10);
        return animation;
    }

    private Animation getRunningAnimation() {
        BufferedImage[] sprites = new BufferedImage[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = this.sprites[i][8];
        }
        return new Animation(sprites);
    }

    private Animation getJumpingAnimation() {
        BufferedImage[] sprites = new BufferedImage[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = this.sprites[i][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        animation.setUpdatesPerFrame(15);
        return animation;
    }

}
