package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.actorstate.ActorIdleState;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class PlayerIdleState extends ActorIdleState<Player> implements PlayerState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerIdleState.class);
    private static final Animation IDLE_ANIMATION = getIdleAnimation();
    private static final Rectangle BOUNDS = new Rectangle(19, 30);

    public PlayerIdleState(Player player, World world) {
        super(player, world);
    }

    @Override
    public Animation getAnimation() {
        return IDLE_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return BOUNDS;
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            actor.changeState(new PlayerRunningState(actor, world));
        } else if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            actor.changeState(new PlayerJumpingState(actor, world));
        } else if (keyboard.isPressed(KeyBinding.CROUCH)) {
            actor.changeState(new PlayerCrouchingState(actor, world));
        } else if (keyboard.isPressed(KeyBinding.TUMBLE)) {
            actor.changeState(new PlayerTumblingState(actor, world));
        } else if (keyboard.isPressed(KeyBinding.DEFEND)) {
            actor.pushState(new PlayerDefendingState(actor, world));
        } else if (keyboard.isPressed(KeyBinding.ATTACK)) {
            actor.pushState(new PlayerAttackingState(actor));
        }
    }

    @Override
    public String toString() {
        return "PlayerIdleState{}";
    }

    @Override
    protected void updateBounds() {
        int boundsY = (actor.getY() + actor.getHeight()) - (int) BOUNDS.getHeight();
        int boundsX;
        int marginX = 5;
        if (actor.getDirection() == Direction.EAST) {
            boundsX = actor.getX() + marginX;
        } else {
            boundsX = actor.getX() + actor.getWidth() - (int) BOUNDS.getWidth() - marginX;
        }
        BOUNDS.setLocation(boundsX, boundsY);
    }

    private static Animation getIdleAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][0];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(15);
        return animation;
    }

}
