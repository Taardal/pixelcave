package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.actorstate.ActorWalkingState;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class PlayerWalkingState extends ActorWalkingState<Player> implements PlayerState {

    private static final Animation WALKING_ANIMATION = getWalkingAnimation();

    public PlayerWalkingState(Player player, World world) {
        super(player, world);
    }

    @Override
    public Animation getAnimation() {
        return WALKING_ANIMATION;
    }

    @Override
    public void draw(Camera camera) {
        if (actor.getDirection() == Direction.EAST) {
            getAnimation().draw(actor, camera);
        } else {
            getAnimation().drawFlippedHorizontally(actor, camera);
        }
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.WEST);
                actor.setVelocity(new Vector2d(-200, 0));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(new Vector2d(200, 0));
            }
        } else {
            actor.changeState(new PlayerIdleState(actor, world));
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            actor.setVelocity(new Vector2d(actor.getVelocity().getX(), -200));
            actor.changeState(new PlayerJumpingState(actor, world));
        }
        if (keyboard.isPressed(KeyBinding.ATTACK)) {
            actor.pushState(new PlayerAttackingState(actor));
        }
    }

    private static Animation getWalkingAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][8];
        }
        return new Animation(sprites);
    }
}
