package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.sprite.SpriteSheetBuilder;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class PlayerIdleState extends ActorIdleState implements PlayerState {

    private static final SpriteSheet SPRITE_SHEET = new SpriteSheetBuilder().directory("scorpion").fileName("scorpion-black-sheet-x1.png").spriteWidth(16).spriteHeight(16).build();
    private static final AnimatedSprite ANIMATED_SPRITE = getAnimatedSprite();

    public PlayerIdleState(Actor actor, World world) {
        super(actor, world);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setVelocity(Vector2d.zero());
        actor.setAnimatedSprite(ANIMATED_SPRITE);
    }

    @Override
    public PlayerState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            return new PlayerWalkingState(actor, world);
        } else if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            return new PlayerJumpingState(actor, world);
        } else {
        }
        return null;
    }

    private static AnimatedSprite getAnimatedSprite() {
        Sprite[] sprites = new Sprite[6];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = SPRITE_SHEET.getSprites()[i][0];
        }
        return new AnimatedSprite(sprites);
    }

}
