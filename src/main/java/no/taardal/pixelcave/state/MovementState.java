package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class MovementState<T extends Actor> implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovementState.class);

    T actor;
    StateListener stateListener;

    MovementState(T actor, StateListener stateListener) {
        this.actor = actor;
        this.stateListener = stateListener;
    }

    boolean isStandingOnSolidTile(World world) {
        int leftColumn = ((int) actor.getX()) / world.getTileWidth();
        int rightColumn = (((int) actor.getX()) + actor.getWidth() - 1) / world.getTileWidth();
        return getDistanceToClosestSolidTileBelow(leftColumn, rightColumn, world) <= 0;
    }

    void step(World world, float secondsSinceLastUpdate) {
        Vector2f nextPosition = actor.getPosition().add(actor.getVelocity().multiply(secondsSinceLastUpdate));
        nextPosition.setX(actor.getPosition().getX() + getDistanceToMoveX(nextPosition, world));
        nextPosition.setY(actor.getPosition().getY() + getDistanceToMoveY(nextPosition, world));
        actor.setPosition(nextPosition);
    }

    private float getDistanceToMoveX(Vector2f nextPosition, World world) {
        float distanceToMoveX = nextPosition.getX() - actor.getPosition().getX();
        float distanceToClosestSolidTileX = getDistanceToClosestSolidTileX(world);
        if (Math.abs(distanceToClosestSolidTileX) < Math.abs(distanceToMoveX)) {
            return distanceToClosestSolidTileX;
        } else {
            return distanceToMoveX;
        }
    }

    private float getDistanceToClosestSolidTileX(World world) {
        int topRow = ((int) actor.getPosition().getY()) / world.getTileHeight();
        int bottomRow = (((int) actor.getPosition().getY()) + actor.getHeight() - 1) / world.getTileHeight();
        if (actor.getDirection() == Direction.RIGHT) {
            return getDistanceToClosestSolidTileRight(topRow, bottomRow, world);
        } else {
            return getDistanceToClosestSolidTileLeft(topRow, bottomRow, world);
        }
    }

    private float getDistanceToClosestSolidTileRight(int topRow, int bottomRow, World world) {
        float collisionX = actor.getPosition().getX() + actor.getWidth();
        int collisionColumn = ((int) collisionX) / world.getTileWidth();
        float distanceToClosestTile = world.getWidth() * world.getTileWidth();
        for (int row = topRow; row <= bottomRow; row++) {
            for (int column = collisionColumn; column < world.getWidth(); column++) {
                if (isSolidTile(column, row, world)) {
                    int tileCollisionX = column * world.getTileWidth();
                    float distanceToTile = tileCollisionX - collisionX;
                    if (distanceToTile < distanceToClosestTile) {
                        distanceToClosestTile = distanceToTile;
                    }
                    break;
                }
            }
        }
        return distanceToClosestTile > 0 ? distanceToClosestTile : 0;
    }

    private float getDistanceToClosestSolidTileLeft(int topRow, int bottomRow, World world) {
        float collisionX = actor.getPosition().getX();
        int collisionColumn = ((int) collisionX) / world.getTileWidth();
        float distanceToClosestTile = -collisionX;
        for (int row = topRow; row <= bottomRow; row++) {
            for (int column = collisionColumn; column >= 0; column--) {
                if (isSolidTile(column, row, world)) {
                    int tileCollisionX = (column * world.getTileWidth()) + world.getTileWidth();
                    float distanceToTile = tileCollisionX - collisionX;
                    if (Math.abs(distanceToTile) < Math.abs(distanceToClosestTile)) {
                        distanceToClosestTile = distanceToTile;
                    }
                    break;
                }
            }
        }
        return Math.abs(distanceToClosestTile) > 0 ? distanceToClosestTile : 0;
    }

    private float getDistanceToMoveY(Vector2f nextPosition, World world) {
        float distanceToMoveY = nextPosition.getY() - actor.getPosition().getY();
        float distanceToClosestSolidTileY = getDistanceToClosestSolidTileY(world);
        if (Math.abs(distanceToClosestSolidTileY) < Math.abs(distanceToMoveY)) {
            return distanceToClosestSolidTileY;
        } else {
            return distanceToMoveY;
        }
    }

    private float getDistanceToClosestSolidTileY(World world) {
        int leftColumn = ((int) actor.getX()) / world.getTileWidth();
        int rightColumn = (((int) actor.getX()) + actor.getWidth() - 1) / world.getTileWidth();
        if (actor.getVelocity().getY() < 0) {
            return getDistanceToClosestSolidTileAbove(leftColumn, rightColumn, world);
        } else {
            return getDistanceToClosestSolidTileBelow(leftColumn, rightColumn, world);
        }
    }

    private float getDistanceToClosestSolidTileBelow(int leftColumn, int rightColumn, World world) {
        float collisionY = actor.getPosition().getY() + actor.getHeight();
        int collisionRow = ((int) collisionY) / world.getTileHeight();
        float distanceToClosestTile = world.getHeight() * world.getTileHeight();
        for (int column = leftColumn; column <= rightColumn; column++) {
            for (int row = collisionRow; row < world.getHeight(); row++) {
                if (isSolidTile(column, row, world)) {
                    int tileCollisionY = row * world.getTileHeight();
                    float distanceToTile = tileCollisionY - collisionY;
                    if (distanceToTile < distanceToClosestTile) {
                        distanceToClosestTile = distanceToTile;
                    }
                    break;
                }
            }
        }
        return distanceToClosestTile > 0 ? distanceToClosestTile : 0;
    }

    private float getDistanceToClosestSolidTileAbove(int leftColumn, int rightColumn, World world) {
        float collisionY = actor.getPosition().getY();
        int collisionRow = ((int) collisionY) / world.getTileHeight();
        float distanceToClosesTile = -collisionY;
        for (int column = leftColumn; column <= rightColumn; column++) {
            for (int row = collisionRow; row >= 0; row--) {
                int tileCollisionY = (row * world.getTileHeight()) + world.getTileHeight();
                float distanceToTile = tileCollisionY - collisionY;
                if (isSolidTile(column, row, world)) {
                    if (Math.abs(distanceToTile) < Math.abs(distanceToClosesTile)) {
                        distanceToClosesTile = distanceToTile;
                    }
                    break;
                }
            }
        }
        return Math.abs(distanceToClosesTile) > 0 ? distanceToClosesTile : 0;
    }

    private boolean isSolidTile(int column, int row, World world) {
        Tile tile = getTile(column, row, world);
        return tile != null && !tile.isSlope();
    }

    private Tile getTile(int column, int row, World world) {
        TileLayer tileLayer = (TileLayer) world.getLayers().get("main");
        int tileId = tileLayer.getTileGrid()[column][row];
        return world.getTiles().get(tileId);
    }

}
