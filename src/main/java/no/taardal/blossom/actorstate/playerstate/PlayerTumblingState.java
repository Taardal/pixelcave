package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class PlayerTumblingState implements PlayerState {

    private static final Animation TUMBLING_ANIMATION = getTumblingAnimation();

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
    }

    @Override
    public void draw(Camera camera) {
        if (player.getDirection() == Direction.EAST) {
            getAnimation().draw(player, camera);
        } else {
            getAnimation().drawFlippedHorizontally(player, camera);
        }
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

}
