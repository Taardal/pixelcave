package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;

public class PlayerAttackState implements PlayerState {

    private static final Animation ATTACK_ANIMATION = getAttackAnimation();

    private Player player;
    private World world;

    public PlayerAttackState(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public void onEntry() {
        player.setAnimation(ATTACK_ANIMATION);
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        if (ATTACK_ANIMATION.isFinished()) {
            player.popState();
        }
    }

    @Override
    public void onExit() {
        ATTACK_ANIMATION.reset();
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    @Override
    public String toString() {
        return "PlayerAttackState{}";
    }

    private static Animation getAttackAnimation() {
        Sprite[] sprites = new Sprite[9];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][1];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;
    }

}
