package no.taardal.blossom.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.layer.TileLayer;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActorFallingState<T extends Actor> implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorFallingState.class);
    private static final double GRAVITY = 500;
    private static final int TERMINAL_VELOCITY = 300;

    protected T actor;
    protected World world;
    protected boolean falling;

    public ActorFallingState(T actor, World world) {
        this.actor = actor;
        this.world = world;
        falling = true;
    }

    @Override
    public void onEntry() {
        falling = true;
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        getAnimation().update();
        if (!falling && actor.getVelocity().getY() >= 0) {
            onLanded();
        } else {
            fall(secondsSinceLastUpdate);
        }
        updateBounds();
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

    @Override
    public String toString() {
        return "ActorFallingState{}";
    }

    protected abstract void updateBounds();

    protected abstract void onLanded();

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
        int tileId = ((TileLayer) world.getLayers().get("main")).getTileGrid()[column][row];
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
                int y = (row * world.getTileHeight()) - actor.getHeight();
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

    public boolean isOnGround() {
        int column = actor.getX() / world.getTileWidth();
        int row = (actor.getY() + actor.getHeight()) / world.getTileHeight();
        int tileId = ((TileLayer) world.getLayers().get("main")).getTileGrid()[column][row];
        return tileId != World.NO_TILE_ID;
    }

}
