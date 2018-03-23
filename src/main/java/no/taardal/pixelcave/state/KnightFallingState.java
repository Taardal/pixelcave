package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;

public class KnightFallingState extends MovementState<Knight> {

    private static final int TERMINAL_VELOCITY = 300;

    public KnightFallingState(Knight actor, StateListener stateListener) {
        super(actor, stateListener);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        Animation animation = actor.getSpriteSheet().getAnimations().get(Animation.Type.FALL);
        actor.setAnimation(animation);
        actor.setVelocity(actor.getVelocity().withY(25));

        actor.setBoundsWidth(animation.getWidth());
        actor.setBoundsHeight(animation.getHeight());
        float boundsX = (actor.getX() + actor.getWidth()) - (animation.getWidth());
        float boundsY = actor.getY() + actor.getHeight() - animation.getHeight();
        actor.setBoundsPosition(new Vector2f(boundsX, boundsY));
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        super.update(world, secondsSinceLastUpdate);

        float velocityY = actor.getVelocity().getY() + (World.GRAVITY * secondsSinceLastUpdate);
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
        actor.setVelocity(actor.getVelocity().withY(velocityY));

        Vector2f nextPosition = actor.getPosition().add(actor.getVelocity().multiply(secondsSinceLastUpdate));
        int nextBottomRow = actor.getBottomRow(nextPosition);
        boolean landed = false;
        if (isVerticalCollision(nextBottomRow, world)) {
            float y = (nextBottomRow * world.getTileHeight()) - actor.getHeight();
            nextPosition = nextPosition.withY(y);
            actor.setVelocity(actor.getVelocity().withY(0));
            landed = true;
        }
        Vector2f distanceToMove = nextPosition.subtract(actor.getPosition());
        actor.setPosition(actor.getPosition().add(distanceToMove));
        actor.setBoundsPosition(actor.getBoundsPosition().add(distanceToMove));

        if (landed) {
            stateListener.onChangeState(new KnightIdleState(actor, stateListener));
        }
    }
}
