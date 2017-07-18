package no.taardal.blossom.actorstate.playerstate;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.actorstate.ActorHurtState;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;

import java.awt.*;

public class PlayerHurtState extends ActorHurtState<Player, Actor> implements PlayerState {

    private static final Animation HURT_ANIMATION = getHurtAnimation();

    public PlayerHurtState(Player player, Actor attacker) {
        super(player, attacker);
    }

    @Override
    protected void updateBounds() {

    }

    @Override
    public Animation getAnimation() {
        return HURT_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        if (actor.getHealth() <= 0) {
            actor.pushState(new PlayerDeadState(actor));
        } else {
            getAnimation().update();
            if (getAnimation().isFinished()) {
                actor.popState();
            }
        }
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    private static Animation getHurtAnimation() {
        Sprite[] sprites = new Sprite[2];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i][6];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(5);
        animation.setIndefinite(false);
        return animation;

    }
}
