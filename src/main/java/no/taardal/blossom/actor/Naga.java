package no.taardal.blossom.actor;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.animation.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Naga extends Enemy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Knight.class);

    public enum Theme {
        VIOLET,
        MAGMA
    }

    public Naga(SpriteSheet spriteSheet) {
        super(spriteSheet);
        //movementStateMachine.pushState(new NagaIdleState(this, movementStateMachine));
    }

    @Override
    public void onAttacked(Actor attacker) {

    }

    @Override
    public void draw(Camera camera) {
        if (direction == Direction.EAST) {
            getCurrentAnimation().drawFlippedHorizontally(this, camera);
        } else {
            super.draw(camera);
        }
    }

    protected Map<String, Animation> createAnimations() {
        Map<String, Animation> animations = new HashMap<>();
        animations.put("IDLE", getIdleAnimation());
        animations.put("RUNNING", getRunningAnimation());
        animations.put("ATTACKING", getAttackingAnimation());
        animations.put("HURT", getHurtAnimation());
        animations.put("DEATH", getDeathAnimation());
        return animations;
    }

    private Animation getIdleAnimation() {
        Sprite[] sprites = new Sprite[6];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][0];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(10);
        return animation;
    }

    private Animation getRunningAnimation() {
        Sprite[] sprites = new Sprite[7];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][1];
        }
        return new Animation(sprites);
    }

    private Animation getAttackingAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][2];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getHurtAnimation() {
        Sprite[] sprites = new Sprite[3];
        for (int i = 0; i < sprites.length; i++) {
             sprites[i] = spriteSheet.getSprites()[i][4];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;

    }

    private Animation getDeathAnimation() {
        Sprite[] sprites = new Sprite[11];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][6];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

}
