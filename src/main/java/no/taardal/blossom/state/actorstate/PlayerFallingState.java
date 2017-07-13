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

public class PlayerFallingState extends ActorFallingState implements PlayerState {

    private static final SpriteSheet SPRITE_SHEET = new SpriteSheetBuilder().directory("scorpion").fileName("scorpion-black-sheet-x1.png").spriteWidth(16).spriteHeight(16).build();
    private static final AnimatedSprite FALLING_ANIMATED_SPRITE = getFallingAnimatedSprite();

    public PlayerFallingState(Actor actor, World world) {
        super(actor, world);
    }

    @Override
    public ActorState getIdleState() {
        return new PlayerIdleState(actor, world);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setAnimatedSprite(FALLING_ANIMATED_SPRITE);
        actor.setVelocity(new Vector2d(actor.getVelocity().getX(), 1.0));
    }

    @Override
    public PlayerState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                if (actor.getDirection() != Direction.WEST) {
                    actor.setDirection(Direction.WEST);
                }
                actor.setVelocity(new Vector2d(-200, actor.getVelocity().getY()));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(new Vector2d(200, actor.getVelocity().getY()));
            }
        }
        return null;
    }

    private static AnimatedSprite getFallingAnimatedSprite() {
        Sprite[] sprites = new Sprite[6];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = SPRITE_SHEET.getSprites()[i][0];
        }
        return new AnimatedSprite(sprites);
    }

}
