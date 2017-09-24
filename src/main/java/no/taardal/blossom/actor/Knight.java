package no.taardal.blossom.actor;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.state.actor.PlayerState;
import no.taardal.blossom.state.actor.knight.KnightFallingState;
import no.taardal.blossom.vector.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
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
        YOUNG

    }

    public Knight(SpriteSheet spriteSheet) {
        super(spriteSheet);
        position = new Vector2d(0, 133);
        velocity = Vector2d.zero();
        direction = Direction.EAST;

        health = 100;
        damage = 50;
        attackRange = 20;
        movementSpeed = 100;

        pushState(new KnightFallingState(this));
    }

    @Override
    protected Map<String, Animation> createAnimations() {
        Map<String, Animation> animations = new HashMap<>();
        animations.put("IDLE", getIdleAnimation());
        animations.put("FALLING", getFallingAnimation());
        animations.put("LANDING", getLandingAnimation());
        animations.put("JUMPING", getJumpingAnimation());
        animations.put("RUNNING", getRunningAnimation());
        animations.put("TUMBLING", getTumblingAnimation());
        animations.put("ATTACKING", getAttackingAnimation());
        animations.put("ATTACKING_MID_AIR", getAttackingMidAirAnimation());
        animations.put("ATTACKING_WHILE_CROUCHED", getAttackingWhileCrouchedAnimation());
        animations.put("DEFENDING", getDefendingAnimation());
        animations.put("DEFENDING_WHILE_CROUCHED", getDefendingWhileCrouchedAnimation());
        animations.put("CROUCHING", getCrouchingAnimation());
        animations.put("HURT", getHurtAnimation());
        animations.put("DEATH", getDeathAnimation());
        return animations;
    }

    @Override
    public void onAttacked(Actor attacker) {

    }

    @Override
    public void draw(Camera camera) {
        if (direction == Direction.WEST) {
            getCurrentAnimation().drawFlippedHorizontally(this, camera);
        } else {
            super.draw(camera);
        }
        if (getBounds() != null) {
            camera.drawBounds(getBounds(), Color.RED);
        }
    }

    public void handleInput(Keyboard keyboard) {
        if (!states.isEmpty()) {
            PlayerState playerState = (PlayerState) states.getFirst();
            playerState.handleInput(keyboard);
        }
    }

    private Animation getIdleAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][0];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(15);
        return animation;
    }

    private Animation getFallingAnimation() {
        Sprite[] sprites = {spriteSheet.getSprites()[6][10]};
        Animation animation = new Animation(sprites);
        animation.setIndefinite(true);
        return animation;
    }

    private Animation getLandingAnimation() {
        Sprite[] sprites = new Sprite[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i + 7][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getJumpingAnimation() {
        Sprite[] sprites = new Sprite[6];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getRunningAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][8];
        }
        return new Animation(sprites);
    }

    private Animation getTumblingAnimation() {
        Sprite[] sprites = new Sprite[8];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][15];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getAttackingMidAirAnimation() {
        Sprite[] sprites = new Sprite[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][11];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(7);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getAttackingAnimation() {
        Sprite[] sprites = new Sprite[9];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][1];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(3);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getAttackingWhileCrouchedAnimation() {
        Sprite[] sprites = new Sprite[8];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][4];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(3);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getDefendingAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][2];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getDefendingWhileCrouchedAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][5];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getCrouchingAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][3];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getHurtAnimation() {
        Sprite[] sprites = new Sprite[2];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][6];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

    private Animation getDeathAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSprites()[i][7];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

}

