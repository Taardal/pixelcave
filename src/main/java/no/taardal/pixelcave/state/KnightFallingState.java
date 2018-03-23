package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
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

        float boundsX = actor.getX() + actor.getWidth() - animation.getWidth();
        float boundsY = actor.getY() + actor.getHeight() - animation.getHeight();
        actor.setCollisionBounds(new Bounds.Builder()
                .setWidth(animation.getWidth())
                .setHeight(animation.getHeight())
                .setPosition(new Vector2f(boundsX, boundsY))
                .build());
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        super.update(world, secondsSinceLastUpdate);

        float velocityY = actor.getVelocity().getY() + (World.GRAVITY * secondsSinceLastUpdate);
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
        actor.setVelocity(actor.getVelocity().withY(velocityY));

        Vector2f nextCollisionBoundsPosition = actor.getCollisionBounds().getPosition().add(actor.getVelocity().multiply(secondsSinceLastUpdate));
        int nextBottomRow = (((int) nextCollisionBoundsPosition.getY())  + actor.getCollisionBounds().getHeight()) / world.getTileHeight();
        boolean landed = false;
        if (isVerticalCollision(nextBottomRow, world)) {
            float y = (nextBottomRow * world.getTileHeight()) - actor.getCollisionBounds().getHeight();
            nextCollisionBoundsPosition = nextCollisionBoundsPosition.withY(y);
            actor.setVelocity(actor.getVelocity().withY(0));
            landed = true;
        }
        Vector2f distanceToMove = nextCollisionBoundsPosition.subtract(actor.getCollisionBounds().getPosition());
        actor.setPosition(actor.getPosition().add(distanceToMove));
        actor.getCollisionBounds().setPosition(actor.getCollisionBounds().getPosition().add(distanceToMove));

        if (landed) {
            stateListener.onChangeState(new KnightIdleState(actor, stateListener));
        }
    }
}
