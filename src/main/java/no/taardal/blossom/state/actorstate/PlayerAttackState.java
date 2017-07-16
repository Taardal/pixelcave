package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;

public class PlayerAttackState implements PlayerState {

    private static final Animation ATTACK_ANIMATION = getAttackAnimation();

    private Player player;

    public PlayerAttackState(Player player) {
        this.player = player;
    }

    @Override
    public Animation getAnimation() {
        return ATTACK_ANIMATION;
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
    public void draw(Camera camera) {
        if (player.getDirection() == Direction.EAST) {
            getAnimation().draw(player, camera);
        } else {
            getAnimation().drawFlippedHorizontally(player, camera);
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
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
