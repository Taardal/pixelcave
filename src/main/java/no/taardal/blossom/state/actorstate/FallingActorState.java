package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FallingActorState implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(FallingActorState.class);
    private static final double GRAVITY = 500;
    private static final int TERMINAL_VELOCITY = 300;

    private Actor actor;
    private World world;

    public FallingActorState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
    }

    @Override
    public String toString() {
        return "FallingActorState{}";
    }

    @Override
    public void onEntry() {

    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                if (actor.getDirection() != Direction.WEST) {
                    actor.setDirection(Direction.WEST);
                }
                actor.setVelocity(new Vector2d(-200, actor.getVelocity().getY()));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(new Vector2d(200, actor.getVelocity().getY()));
            }
        }
        return null;
    }

    @Override
    public ActorState update(double secondsSinceLastUpdate) {
        if (actor.isOnGround() && actor.getVelocity().getY() > 0) {
            return new IdleActorState(actor, world);
        } else {
            fall(secondsSinceLastUpdate);
            return null;
        }
    }

    private void fall(double secondsSinceLastUpdate) {
        Vector2d vector2d = actor.getVelocity().multiply(secondsSinceLastUpdate);
        actor.getPosition().addAssign(vector2d);
        double velocityY = actor.getVelocity().getY() + (GRAVITY * secondsSinceLastUpdate);
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
        actor.setVelocity(new Vector2d(actor.getVelocity().getX(), velocityY));
    }

}
