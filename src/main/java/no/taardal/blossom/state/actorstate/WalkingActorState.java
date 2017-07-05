package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalkingActorState implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(WalkingActorState.class);

    private Actor actor;
    private World world;

    public WalkingActorState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
    }

    @Override
    public void onEntry() {
        if (actor.getDirection() == Direction.WEST) {
            actor.setVelocity(new Vector2d(-200, 0));
        } else if (actor.getDirection() == Direction.EAST) {
            actor.setVelocity(new Vector2d(200, 0));
        }
    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            actor.setFalling(true);
            actor.setVelocity(new Vector2d(actor.getVelocity().getX(), -200));
            return new FallingActorState(actor, world);
        }
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.WEST);
                actor.setVelocity(new Vector2d(-200, 0));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(new Vector2d(200, 0));
            }
            return null;
        }
        return new IdleActorState(actor, world);
    }

    @Override
    public ActorState update(double timeSinceLastUpdate) {
        Vector2d vector2d = actor.getVelocity().multiply(timeSinceLastUpdate);
        actor.getPosition().addAssign(vector2d);
        return null;
    }

    @Override
    public String toString() {
        return "WalkingActorState{}";
    }

}
