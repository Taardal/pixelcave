package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class PlayerFallingState extends ActorFallingState implements PlayerState {

    private static final Animation FALLING_ANIMATED_SPRITE = getFallingAnimatedSprite();

    public PlayerFallingState(Player player, World world) {
        super(player, world);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setAnimation(FALLING_ANIMATED_SPRITE);
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                if (actor.getDirection() != Direction.WEST) {
                    actor.setDirection(Direction.WEST);
                }
                actor.setVelocity(new Vector2d(-200, actor.getVelocity().getY()));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                if (actor.getDirection() != Direction.EAST) {
                    actor.setDirection(Direction.EAST);
                }
                actor.setVelocity(new Vector2d(200, actor.getVelocity().getY()));
            }
        }
    }

    @Override
    public void onLanded() {
        actor.changeState(new PlayerIdleState((Player) actor, world));
    }

    private static Animation getFallingAnimatedSprite() {
        Sprite[] sprites = new Sprite[6];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][0];
        }
        return new Animation(sprites);
    }

}
