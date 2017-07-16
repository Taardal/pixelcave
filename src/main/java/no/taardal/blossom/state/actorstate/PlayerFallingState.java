package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class PlayerFallingState extends ActorFallingState implements PlayerState {

    private static final Animation FALLING_ANIMATION = getFallingAnimation();
    private static final Animation LANDING_ANIMATION = getLandingAnimation();
    private static final int VELOCITY_X = 200;

    public PlayerFallingState(Player player, World world) {
        super(player, world);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setAnimation(FALLING_ANIMATION);
    }

    @Override
    public void onExit() {
        super.onExit();
        FALLING_ANIMATION.reset();
        LANDING_ANIMATION.reset();
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        super.update(secondsSinceLastUpdate);
        if (falling && actor.getVelocity().getY() > 0) {
            actor.setAnimation(FALLING_ANIMATION);
        }
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                if (actor.getDirection() != Direction.WEST) {
                    actor.setDirection(Direction.WEST);
                }
                actor.setVelocity(new Vector2d(-VELOCITY_X, actor.getVelocity().getY()));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                if (actor.getDirection() != Direction.EAST) {
                    actor.setDirection(Direction.EAST);
                }
                actor.setVelocity(new Vector2d(VELOCITY_X, actor.getVelocity().getY()));
            }
        } else {
            actor.setVelocity(new Vector2d(0, actor.getVelocity().getY()));
        }
    }

    @Override
    public void onLanded() {
        if (!actor.getAnimation().equals(LANDING_ANIMATION)) {
            actor.setAnimation(LANDING_ANIMATION);
        }
        if (actor.getVelocity().getX() == 0) {
            if (LANDING_ANIMATION.isFinished()) {
                actor.changeState(new PlayerIdleState((Player) actor, world));
            }
        } else {
            actor.changeState(new PlayerIdleState((Player) actor, world));
        }
    }

    private static Animation getFallingAnimation() {
        Sprite[] sprites = new Sprite[]{Player.SPRITE_SHEET.getSprites()[6][10]};
        Animation animation = new Animation(sprites);
        animation.setIndefinite(true);
        return animation;
    }

    private static Animation getLandingAnimation() {
        Sprite[] sprites = new Sprite[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Player.SPRITE_SHEET.getSprites()[i + 7][10];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(8);
        animation.setIndefinite(false);
        return animation;
    }


}
