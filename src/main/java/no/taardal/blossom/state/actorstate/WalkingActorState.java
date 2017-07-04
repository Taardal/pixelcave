package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.vector.Vector2i;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalkingActorState implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(WalkingActorState.class);
    private static final Vector2i velocity = new Vector2i(200, 0);

    private Actor actor;
    private World world;

    public WalkingActorState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
    }

    @Override
    public void onEntry() {

    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.WEST);
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
            }
            return null;
        } else {
            return new IdleActorState(actor, world);
        }
    }

    @Override
    public ActorState update(double timeSinceLastUpdate) {
        Vector2i direction = velocity.multiply(timeSinceLastUpdate);
        if (actor.getDirection() == Direction.EAST) {
            actor.getPosition().addEq(direction);
        } else {
            actor.getPosition().subtractEq(direction);
        }
        return null;
    }

    @Override
    public String toString() {
        return "WalkingActorState{}";
    }

}
