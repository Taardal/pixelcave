package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.vector.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActorRunningState<T extends Actor> extends ActorState<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorRunningState.class);

    protected Vector2d distanceWalked;

    public ActorRunningState(T actor, StateMachine stateMachine) {
        super(actor, stateMachine);
        distanceWalked = Vector2d.zero();
    }

    @Override
    public void onEntry() {
        actor.setVelocity(getVelocity());
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        distanceWalked = actor.getVelocity().multiply(secondsSinceLastUpdate);
        actor.setPosition(actor.getPosition().add(distanceWalked));
        super.update(secondsSinceLastUpdate);
    }

    protected Vector2d getVelocity() {
        if (actor.getDirection() == Direction.WEST) {
            return new Vector2d(-actor.getMovementSpeed(), actor.getVelocity().getY());
        } else {
            return new Vector2d(actor.getMovementSpeed(), actor.getVelocity().getY());
        }
    }

}
