package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActorFallingState implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorFallingState.class);
    private static final double GRAVITY = 500;
    private static final int TERMINAL_VELOCITY = 300;

    Actor actor;
    World world;
    boolean falling;

    public ActorFallingState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
        falling = true;
    }

    public abstract ActorState getIdleState();

    @Override
    public String toString() {
        return "ActorFallingState{}";
    }

    @Override
    public void onEntry() {
        falling = true;
    }

    @Override
    public ActorState update(double secondsSinceLastUpdate) {
        if (!falling && actor.getVelocity().getY() >= 0) {
            return getIdleState();
        } else {
            fall(secondsSinceLastUpdate);
            return null;
        }
    }

    void fall(double secondsSinceLastUpdate) {
        actor.setPosition(getNextPosition(secondsSinceLastUpdate));
        actor.setVelocity(getNextVelocity(secondsSinceLastUpdate));
    }

    private Vector2d getNextPosition(double secondsSinceLastUpdate) {
        Vector2d distance = actor.getVelocity().multiply(secondsSinceLastUpdate);
        Vector2d nextPosition = actor.getPosition().add(distance);
        int column = (int) nextPosition.getX() / world.getTileWidth();
        int actorBottomY = (int) nextPosition.getY() + actor.getHeight();
        int row = actorBottomY / world.getTileHeight();
        int tileId = world.getLayers().get("main").getTileGrid()[column][row];
        if (tileId != World.NO_TILE_ID) {
            Tile tile = world.getTiles().get(tileId);
            if (tile.isSlope()) {
                int slopeY = getSlopeY(column, row, tile);
                if (actorBottomY > slopeY) {
                    int y = slopeY - actor.getHeight();
                    nextPosition = new Vector2d(nextPosition.getX(), y);
                    falling = false;
                }
            } else {
                int y = (row - 1) * world.getTileHeight();
                nextPosition = new Vector2d(nextPosition.getX(), y);
                falling = false;
            }
        }
        return nextPosition;
    }

    private int getSlopeY(int column, int row, Tile tile) {
        int slopeCollisionX = actor.getX() + (actor.getWidth() / 2);
        int tileX = column * world.getTileWidth();
        int tileY = row * world.getTileHeight();
        if (tile.getDirection() == Direction.EAST) {
            return (tileY + world.getTileHeight()) - (slopeCollisionX - tileX);
        } else {
            return (tileY + world.getTileHeight()) - ((tileX + world.getTileWidth()) - slopeCollisionX);
        }
    }

    private Vector2d getNextVelocity(double secondsSinceLastUpdate) {
        double velocityY = actor.getVelocity().getY() + (GRAVITY * secondsSinceLastUpdate);
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
        return new Vector2d(actor.getVelocity().getX(), velocityY);
    }

}
