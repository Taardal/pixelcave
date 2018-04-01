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
        animations.put(Animation.Type.RUN, getRunAnimation());
        animations.put(Animation.Type.JUMP, getJumpAnimation());
        animations.put(Animation.Type.FALL, getFallAnimation());
        animations.put(Animation.Type.LAND, getLandAnimation());
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

    private Animation getRunAnimation() {
        BufferedImage[] sprites = new BufferedImage[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = this.sprites[i][8];
        }
        return new Animation(sprites);
    }

    private Animation getJumpAnimation() {
        BufferedImage[] sprites = new BufferedImage[7];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = this.sprites[i][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getFallAnimation() {
        BufferedImage[] sprites = {this.sprites[6][10]};
        Animation animation = new Animation(sprites);
        animation.setIndefinite(true);
        return animation;
    }

    private Animation getLandAnimation() {
        BufferedImage[] sprites = new BufferedImage[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = this.sprites[i + 7][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

}
