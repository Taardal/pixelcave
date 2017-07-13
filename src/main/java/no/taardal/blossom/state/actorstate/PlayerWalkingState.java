package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.sprite.SpriteSheetBuilder;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class PlayerWalkingState extends ActorWalkingState implements PlayerState {

    private static final SpriteSheet SPRITE_SHEET = new SpriteSheetBuilder().directory("scorpion").fileName("scorpion-black-sheet-x1.png").spriteWidth(16).spriteHeight(16).build();
    private static final AnimatedSprite ANIMATED_SPRITE = getAnimatedSprite();

    public PlayerWalkingState(Actor actor, World world) {
        super(actor, world);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setAnimatedSprite(ANIMATED_SPRITE);
    }

    @Override
    public PlayerState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            actor.setVelocity(new Vector2d(actor.getVelocity().getX(), -200));
            return new PlayerJumpingState(actor, world);
        }
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.WEST);
                actor.setVelocity(new Vector2d(-200, 0));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(new Vector2d(200, 0));
            }
            return null;
        }
        return new PlayerIdleState(actor, world);
    }

    private static AnimatedSprite getAnimatedSprite() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = SPRITE_SHEET.getSprites()[i][1];
        }
        return new AnimatedSprite(sprites);
    }

}
