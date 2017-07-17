package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;

public class PlayerAttackingWhileCrouchedState implements PlayerState {

    private static final Animation ATTACKING_WHILE_CROUCHED_ANIMATION = getAttackingWhileCrouchedAnimation();

    private Player player;
    private World world;

    public PlayerAttackingWhileCrouchedState(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public Animation getAnimation() {
        return ATTACKING_WHILE_CROUCHED_ANIMATION;
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void update(double timeSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().isFinished()) {
            player.popState();
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    private static Animation getAttackingWhileCrouchedAnimation() {
        Sprite[] sprites = new Sprite[8];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][4];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(3);
        animation.setIndefinite(false);
        return animation;
    }
}
