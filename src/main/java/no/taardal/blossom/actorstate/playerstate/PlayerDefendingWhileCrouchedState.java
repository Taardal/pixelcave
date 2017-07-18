package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;

import java.awt.*;

public class PlayerDefendingWhileCrouchedState implements PlayerState {

    private static final Animation DEFENDING_WHILE_CROUCHED_ANIMATION = getDefendingWhileCrouchedAnimation();
    private static final Rectangle BOUNDS = new Rectangle(19, 20);

    private Player player;
    private World world;

    public PlayerDefendingWhileCrouchedState(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public Animation getAnimation() {
        return DEFENDING_WHILE_CROUCHED_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return BOUNDS;
    }

    @Override
    public void onEntry() {
        updateBounds();
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (!keyboard.isPressed(KeyBinding.CROUCH) || !keyboard.isPressed(KeyBinding.DEFEND)) {
            player.popState();
        }
    }

    private static Animation getDefendingWhileCrouchedAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][5];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

    private void updateBounds() {
        int boundsY = (player.getY() + player.getHeight()) - (int) BOUNDS.getHeight();
        int boundsX;
        if (player.getDirection() == Direction.EAST) {
            boundsX = player.getX() + 5;
        } else {
            boundsX = player.getX() + player.getWidth() - (int) BOUNDS.getWidth() - 5;
        }
        BOUNDS.setLocation(boundsX, boundsY);
    }

}
