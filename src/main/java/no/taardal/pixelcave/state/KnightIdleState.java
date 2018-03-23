package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;

public class KnightIdleState extends MovementState<Knight> {

    public KnightIdleState(Knight actor, StateListener stateListener) {
        super(actor, stateListener);
    }

    @Override
    public void onEntry() {
        Animation animation = actor.getSpriteSheet().getAnimations().get(Animation.Type.IDLE);
        actor.setAnimation(animation);
        actor.setVelocity(new Vector2f(0, 0));

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
        int bottomRow = (((int) actor.getCollisionBounds().getY())  + actor.getCollisionBounds().getHeight()) / world.getTileHeight();
        if (!isVerticalCollision(bottomRow, world)) {
            stateListener.onChangeState(new KnightFallingState(actor, stateListener));
        }
    }
}
