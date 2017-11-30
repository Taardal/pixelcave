package no.taardal.pixelcave.state.actor.knight;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.sprite.Sprite;
import no.taardal.pixelcave.state.actor.ActorMovementState;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.vector.Vector2f;

public class KnightRunningState extends ActorMovementState<Knight> {

    private Animation animation;

    public KnightRunningState(Knight actor, StateMachine stateMachine) {
        super(actor, stateMachine);
        animation = getRunningAnimation();
    }

    @Override
    public void onEntry() {
        //actor.getBounds().setWidth(27);
        //actor.getBounds().setHeight(27);
        actor.getBounds().setPosition(new Vector2f(getBoundsX(), getBoundsY()));
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            actor.setPreviousDirection(actor.getDirection());

            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                if (actor.getDirection() == Direction.UP_RIGHT || actor.getDirection() == Direction.DOWN_LEFT) {
                    actor.setDirection(Direction.DOWN_LEFT);
                } else if (actor.getDirection() == Direction.DOWN_RIGHT || actor.getDirection() == Direction.UP_LEFT) {
                    actor.setDirection(Direction.UP_LEFT);
                } else {
                    actor.setDirection(Direction.LEFT);
                }
                actor.setVelocity(new Vector2f(-actor.getMovementSpeed(), actor.getVelocity().getY()));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                if (actor.getDirection() == Direction.UP_LEFT) {
                    actor.setDirection(Direction.DOWN_RIGHT);
                } else if (actor.getDirection() == Direction.DOWN_LEFT) {
                    actor.setDirection(Direction.UP_RIGHT);
                } else {
                    if (actor.getDirection() != Direction.DOWN_RIGHT && actor.getDirection() != Direction.UP_RIGHT) {
                        actor.setDirection(Direction.RIGHT);
                    }
                }
                actor.setVelocity(new Vector2f(actor.getMovementSpeed(), actor.getVelocity().getY()));
            }

        } else {
            stateMachine.changeState(new KnightIdleState(actor, stateMachine));
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            stateMachine.changeState(new KnightJumpingState(actor, stateMachine));
        }
        if (keyboard.isPressed(KeyBinding.CROUCH)) {
        }
        if (keyboard.isPressed(KeyBinding.TUMBLE)) {
        }
        if (keyboard.isPressed(KeyBinding.DEFEND)) {
        }
    }

    @Override
    public void update(float secondsSinceLastUpdate) {
        int column = (actor.getWidth() / 2) / actor.getWorld().getTileWidth();
        int row = (((int) actor.getPosition().getY()) + actor.getHeight()) / actor.getWorld().getTileHeight();
        if (isSolidTile(column, row)) {
            super.update(secondsSinceLastUpdate);
        } else {
            stateMachine.changeState(new KnightFallingState(actor, stateMachine));
        }
    }

    @Override
    public Animation getAnimation() {
        return animation;
    }

    private Animation getRunningAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = actor.getSpriteSheet().getSprites()[i][8];
        }
        return new Animation(sprites);
    }

    private float getBoundsY() {
        return (actor.getY() + actor.getHeight()) - actor.getBounds().getHeight();
    }

    private float getBoundsX() {
        int marginX = 8;
        if (actor.isFacingRight()) {
            return actor.getX() + marginX;
        } else {
            return actor.getX() + actor.getWidth() - actor.getBounds().getWidth() - marginX;
        }
    }

}
