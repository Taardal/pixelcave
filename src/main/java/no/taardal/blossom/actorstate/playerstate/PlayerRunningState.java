package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.actorstate.ActorWalkingState;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

import java.awt.*;

public class PlayerRunningState extends ActorWalkingState<Player> implements PlayerState {

    private static final Animation RUNNING_ANIMATION = getRunningAnimation();
    private static final Rectangle BOUNDS = new Rectangle(27, 27);

    public PlayerRunningState(Player player, World world) {
        super(player, world);
    }

    @Override
    public int getVelocityX() {
        return 100;
    }

    @Override
    public Animation getAnimation() {
        return RUNNING_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return BOUNDS;
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        super.update(secondsSinceLastUpdate);
        updateBounds();
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.WEST);
                actor.setVelocity(new Vector2d(-getVelocityX(), actor.getVelocity().getY()));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(new Vector2d(getVelocityX(), actor.getVelocity().getY()));
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

    @Override
    protected void updateBounds() {
        int boundsY = (actor.getY() + actor.getHeight()) - (int) BOUNDS.getHeight();
        int boundsX;
        if (actor.getDirection() == Direction.EAST) {
            boundsX = actor.getX() + 8;
        } else {
            boundsX = actor.getX() + actor.getWidth() - (int) BOUNDS.getWidth() - 8;
        }
        BOUNDS.setLocation(boundsX, boundsY);
    }

    private static Animation getRunningAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][8];
        }
        return new Animation(sprites);
    }
}
