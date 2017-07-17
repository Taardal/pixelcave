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

public class PlayerRunningState extends ActorWalkingState<Player> implements PlayerState {

    private static final Animation WALKING_ANIMATION = getWalkingAnimation();
    public static final int RUNNING_VELOCITY_X = 200;

    public PlayerRunningState(Player player, World world) {
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
                actor.setVelocity(new Vector2d(-RUNNING_VELOCITY_X, actor.getVelocity().getY()));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(new Vector2d(RUNNING_VELOCITY_X, actor.getVelocity().getY()));
            }
        } else {
            actor.changeState(new PlayerIdleState(actor, world));
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            actor.changeState(new PlayerJumpingState(actor, world));
        }
        if (keyboard.isPressed(KeyBinding.CROUCH)) {
            actor.changeState(new PlayerCrouchingState(actor, world));
        }
        if (keyboard.isPressed(KeyBinding.TUMBLE)) {
            actor.changeState(new PlayerTumblingState(actor, world));
        }
        if (keyboard.isPressed(KeyBinding.DEFEND)) {
            actor.pushState(new PlayerDefendingState(actor, world));
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
