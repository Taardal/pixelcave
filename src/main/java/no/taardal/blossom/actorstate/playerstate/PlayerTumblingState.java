package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

import java.awt.*;

public class PlayerTumblingState implements PlayerState {

    private static final Animation TUMBLING_ANIMATION = getTumblingAnimation();
    private static final Rectangle BOUNDS = new Rectangle(19, 20);

    private Player player;
    private World world;

    public PlayerTumblingState(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    @Override
    public Animation getAnimation() {
        return TUMBLING_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return BOUNDS;
    }

    @Override
    public void onEntry() {
        if (player.getDirection() == Direction.EAST) {
            player.setVelocity(new Vector2d(100, player.getVelocity().getY()));
        } else {
            player.setVelocity(new Vector2d(-100, player.getVelocity().getY()));
        }
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().getFrame() > 2) {
            Vector2d distance = player.getVelocity().multiply(timeSinceLastUpdate);
            player.setPosition(player.getPosition().add(distance));
        }
        if (getAnimation().isFinished()) {
            player.changeState(new PlayerIdleState(player, world));
        }
        updateBounds();
    }

    @Override
    public void onExit() {
        TUMBLING_ANIMATION.reset();
    }

    private static Animation getTumblingAnimation() {
        Sprite[] sprites = new Sprite[8];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][15];
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
