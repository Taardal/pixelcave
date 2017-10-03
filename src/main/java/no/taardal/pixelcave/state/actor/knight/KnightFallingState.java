package no.taardal.pixelcave.state.actor.knight;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.sprite.Sprite;
import no.taardal.pixelcave.state.actor.ActorFallingState;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.vector.Vector2d;

public class KnightFallingState extends ActorFallingState<Knight> {

    public KnightFallingState(Knight actor, StateMachine stateMachine) {
        super(actor, stateMachine);
    }

    @Override
    public void nextMove(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                moveLeft();
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                moveRight();
            }
        } else {
            actor.setVelocity(new Vector2d(0, actor.getVelocity().getY()));
        }
        if (keyboard.isPressed(KeyBinding.ATTACK) && actor.getVelocity().getY() >= 0) {

        }
    }

    @Override
    protected Animation getFallingActorAnimation() {
        Sprite[] sprites = {actor.getSpriteSheet().getSprites()[6][10]};
        Animation animation = new Animation(sprites);
        animation.setIndefinite(true);
        return animation;
    }

    @Override
    protected Animation getLandingActorAnimation() {
        Sprite[] sprites = new Sprite[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = actor.getSpriteSheet().getSprites()[i + 7][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

    @Override
    protected void onLanded() {
        if (actor.getVelocity().getX() == 0) {
            if (getAnimation().isFinished()) {
                stateMachine.changeState(new KnightIdleState(actor, stateMachine));
            }
        } else {
            stateMachine.changeState(new KnightIdleState(actor, stateMachine));
        }
    }

    @Override
    protected void updateBounds() {
        actor.getBounds().setWidth(19);
        actor.getBounds().setHeight(30);
        actor.getBounds().setPosition(getBoundsX(), getBoundsY());
    }

    private int getBoundsY() {
        return (actor.getY() + actor.getHeight()) - actor.getBounds().getHeight();
    }

    private int getBoundsX() {
        int marginX = 5;
        if (actor.getDirection() == Direction.EAST) {
            return actor.getX() + marginX;
        } else {
            return actor.getX() + actor.getWidth() - actor.getBounds().getWidth() - marginX;
        }
    }

    private void moveRight() {
        if (actor.getDirection() != Direction.EAST) {
            actor.setDirection(Direction.EAST);
        }
        actor.setVelocity(new Vector2d(actor.getMovementSpeed(), actor.getVelocity().getY()));
    }

    private void moveLeft() {
        if (actor.getDirection() != Direction.WEST) {
            actor.setDirection(Direction.WEST);
        }
        actor.setVelocity(new Vector2d(-actor.getMovementSpeed(), actor.getVelocity().getY()));
    }

}
