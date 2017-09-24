package no.taardal.blossom.state.actor.naga;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.state.actor.ActorRunningState;
import no.taardal.blossom.state.actor.EnemyState;
import no.taardal.blossom.vector.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NagaRunningState extends ActorRunningState<Naga> implements EnemyState {

    private static final Logger LOGGER = LoggerFactory.getLogger(NagaRunningState.class);
    private static final int MAX_DISTANCE_SAME_DIRECTION = 100;

    private Vector2d distanceWalkedInSameDirection;

    public NagaRunningState(Naga actor) {
        super(actor);
        distanceWalkedInSameDirection = Vector2d.zero();
    }

    @Override
    public void nextMove(Player player) {
        int radius = 50;
        double length = Vector2d.getLength(actor.getPosition(), player.getPosition());
        if (length <= radius) {
            actor.pushState(new NagaAttackingState(actor));
        } else if (Math.abs(distanceWalkedInSameDirection.getX()) > MAX_DISTANCE_SAME_DIRECTION) {
            if (actor.getDirection() == Direction.WEST) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(getVelocity());
            } else {
                actor.setDirection(Direction.WEST);
                actor.setVelocity(getVelocity());
            }
            distanceWalkedInSameDirection = Vector2d.zero();
        } else {
            if (actor.getDirection() == Direction.WEST && actor.getVelocity().getX() > 0) {
                actor.setDirection(Direction.EAST);
            } else if (actor.getDirection() == Direction.EAST && actor.getVelocity().getX() < 0) {
                actor.setDirection(Direction.WEST);
            }
            distanceWalkedInSameDirection = distanceWalkedInSameDirection.add(distanceWalked);
        }
    }

    @Override
    protected void updateBounds() {
        actor.getBounds().setWidth(20);
        actor.getBounds().setHeight(37);
        actor.getBounds().setPosition(getBoundsX(), getBoundsY());
    }

    private int getBoundsY() {
        return (actor.getY() + actor.getHeight()) - actor.getBounds().getHeight();
    }

    private int getBoundsX() {
        int marginX = 8;
        if (actor.getDirection() == Direction.EAST) {
            return actor.getX() + marginX;
        } else {
            return actor.getX() + actor.getWidth() - actor.getBounds().getWidth() - marginX;
        }
    }

}
