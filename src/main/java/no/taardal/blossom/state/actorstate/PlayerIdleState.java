package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerIdleState extends ActorIdleState implements PlayerState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerIdleState.class);
    private static final Animation IDLE_ANIMATION = getIdleAnimation();

    public PlayerIdleState(Player player, World world) {
        super(player, world);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setVelocity(Vector2d.zero());
        actor.setAnimation(IDLE_ANIMATION);
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            actor.changeState(new PlayerWalkingState((Player) actor, world));
        } else if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            actor.changeState(new PlayerJumpingState((Player) actor, world));
        } else if (keyboard.isPressed(KeyBinding.ATTACK)) {
            actor.pushState(new PlayerAttackState((Player) actor, world));
        }
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
