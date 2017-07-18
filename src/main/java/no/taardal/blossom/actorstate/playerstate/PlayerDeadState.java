package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.actorstate.ActorDeadState;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;

import java.awt.*;

public class PlayerDeadState extends ActorDeadState<Player> implements PlayerState {

    private static final Animation DEATH_ANIMATION = getDeathAnimation();

    public PlayerDeadState(Player player) {
        super(player);
    }

    @Override
    public Animation getAnimation() {
        return DEATH_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    private static Animation getDeathAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][7];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;

    }

}
