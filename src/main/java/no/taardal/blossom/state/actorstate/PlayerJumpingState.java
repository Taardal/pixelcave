package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerJumpingState extends PlayerFallingState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerJumpingState.class);
    private static final Animation JUMP_ANIMATION = getJumpAnimation();
    private static final int VELOCITY_Y = -200;

    public PlayerJumpingState(Player player, World world) {
        super(player, world);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setAnimation(JUMP_ANIMATION);
        actor.setVelocity(new Vector2d(actor.getVelocity().getX(), VELOCITY_Y));
    }

    @Override
    public void onExit() {
        super.onExit();
        JUMP_ANIMATION.reset();
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        super.update(secondsSinceLastUpdate);
    }

    private static Animation getJumpAnimation() {
        Sprite[] sprites = new Sprite[5];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][10];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(8);
        animation.setIndefinite(false);
        return animation;
    }

    @Override
    public String toString() {
        return "PlayerJumpingState{}";
    }
}