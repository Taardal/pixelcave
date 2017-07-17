package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;

public class PlayerDefendingState implements PlayerState {

    private static final Animation DEFENDING_ANIMATION = getDefendingAnimation();

    private Player player;
    private World world;

    public PlayerDefendingState(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public Animation getAnimation() {
        return DEFENDING_ANIMATION;
    }

    @Override
    public void onEntry() {

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
        if (!keyboard.isPressed(KeyBinding.DEFEND)) {
            player.popState();
        }
    }

    private static Animation getDefendingAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][2];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(10);
        animation.setIndefinite(false);
        return animation;
    }

}
