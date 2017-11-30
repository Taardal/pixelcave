package no.taardal.pixelcave.state.actor.knight;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.sprite.Sprite;
import no.taardal.pixelcave.state.actor.ActorMovementState;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.vector.Vector2f;

public class KnightFallingState extends ActorMovementState<Knight> {

    private Animation fallingAnimation;
    private Animation landingAnimation;

    public KnightFallingState(Knight actor, StateMachine stateMachine) {
        super(actor, stateMachine);
        fallingAnimation = getFallingAnimation();
        landingAnimation = getLandingAnimation();
    }

    @Override
    public void onEntry() {
        actor.setVelocity(new Vector2f(actor.getVelocity().getX(), 20));
        actor.getBounds().setWidth(19);
        actor.getBounds().setHeight(30);
        actor.getBounds().setPosition(new Vector2f(getBoundsX(), getBoundsY()));
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                moveLeft();
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                moveRight();
            }
        } else {
            actor.setVelocity(new Vector2f(0, actor.getVelocity().getY()));
        }
    }

    @Override
    public void update(float secondsSinceLastUpdate) {
        super.update(secondsSinceLastUpdate);
        if (!actor.isFalling()) {
            onLanded();
        }
    }

    @Override
    public void onExit() {
        fallingAnimation.reset();
        landingAnimation.reset();
    }

    @Override
    public Animation getAnimation() {
        if (actor.isFalling()) {
            return fallingAnimation;
        } else {
            return landingAnimation;
        }
    }

    private Animation getFallingAnimation() {
        Sprite[] sprites = {actor.getSpriteSheet().getSprites()[6][10]};
        Animation animation = new Animation(sprites);
        animation.setIndefinite(true);
        return animation;
    }

    private Animation getLandingAnimation() {
        Sprite[] sprites = new Sprite[3];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = actor.getSpriteSheet().getSprites()[i + 7][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

    private float getBoundsX() {
        int marginX = 5;
        if (actor.getDirection() == Direction.RIGHT) {
            return actor.getX() + marginX;
        } else {
            return actor.getX() + actor.getWidth() - actor.getBounds().getWidth() - marginX;
        }
    }

    private float getBoundsY() {
        return (actor.getY() + actor.getHeight()) - actor.getBounds().getHeight();
    }

    private void moveRight() {
        if (actor.getDirection() != Direction.RIGHT) {
            actor.setDirection(Direction.RIGHT);
        }
        actor.setVelocity(new Vector2f(actor.getMovementSpeed(), actor.getVelocity().getY()));
    }

    private void moveLeft() {
        if (actor.getDirection() != Direction.LEFT) {
            actor.setDirection(Direction.LEFT);
        }
        actor.setVelocity(new Vector2f(-actor.getMovementSpeed(), actor.getVelocity().getY()));
    }

    private void onLanded() {
        if (actor.isRunning()) {
            stateMachine.changeState(new KnightRunningState(actor, stateMachine));
        } else {
            if (getAnimation().isFinished()) {
                stateMachine.changeState(new KnightIdleState(actor, stateMachine));
            }
        }
    }

}
