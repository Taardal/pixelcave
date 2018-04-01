package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MovementState<T extends Actor> implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovementState.class);

    T actor;
    StateListener stateListener;

    public MovementState(T actor, StateListener stateListener) {
        this.actor = actor;
        this.stateListener = stateListener;
    }

    Vector2f checkHorizontalCollision(Vector2f position, World world) {
        if (position.getX() < 0) {
            return position.withX(0);
        } else {
            int nextLeftColumn = ((int) position.getX()) / world.getTileHeight();
            if (isHorizontalCollision(nextLeftColumn, world)) {
                float x = (nextLeftColumn * world.getTileWidth()) + world.getTileWidth();
                position = position.withX(x);
                actor.setVelocity(actor.getVelocity().withX(0));
            }
            int nextRightColumn = (((int) position.getX())  + actor.getBounds().getWidth()) / world.getTileHeight();
            if (isHorizontalCollision(nextRightColumn, world)) {
                float x = (nextRightColumn * world.getTileWidth()) - actor.getBounds().getWidth();
                position = position.withX(x);
                actor.setVelocity(actor.getVelocity().withX(0));
            }
            return position;
        }
    }

    Vector2f checkVerticalCollision(Vector2f nextPosition, World world) {
        if (nextPosition.getY() < 0) {
            return nextPosition.withY(0);
        } else {
            int nextTopRow = ((int) nextPosition.getY()) / world.getTileHeight();
            if (isVerticalCollision(nextTopRow, world)) {
                float y = (nextTopRow * world.getTileHeight()) + world.getTileHeight();
                nextPosition = nextPosition.withY(y);
                actor.setVelocity(actor.getVelocity().withY(0));
            }
            int nextBottomRow = (((int) nextPosition.getY())  + actor.getBounds().getHeight()) / world.getTileHeight();
            if (isVerticalCollision(nextBottomRow, world)) {
                float y = (nextBottomRow * world.getTileHeight()) - actor.getBounds().getHeight();
                nextPosition = nextPosition.withY(y);
                actor.setVelocity(actor.getVelocity().withY(0));
            }
            return nextPosition;
        }
    }

    boolean isHorizontalCollision(int column, World world) {
        int topRow = ((int) actor.getBounds().getY()) / world.getTileHeight();
        int bottomRow = ((int) actor.getBounds().getY() + actor.getBounds().getHeight()) / world.getTileHeight();
        for (int row = topRow; row <= bottomRow; row++) {
            if (isSolidTile(column, row, world)) {
                return true;
            }
        }
        return false;
    }

    boolean isVerticalCollision(int row, World world) {
        int leftColumn = ((int) actor.getBounds().getX()) / world.getTileWidth();
        int rightColumn = ((int) actor.getBounds().getX() + actor.getBounds().getWidth()) / world.getTileWidth();
        for (int column = leftColumn; column <= rightColumn; column++) {
            if (isSolidTile(column, row, world)) {
                return true;
            }
        }
        return false;
    }

    boolean isBottomCollision(World world) {
        int bottomRow = (((int) actor.getBounds().getY())  + actor.getBounds().getHeight()) / world.getTileHeight();
        return isVerticalCollision(bottomRow, world);
    }

    boolean isSolidTile(int column, int row, World world) {
        Tile tile = getTile(column, row, world);
        return tile != null && !tile.isSlope();
    }

    private Tile getTile(int column, int row, World world) {
        TileLayer tileLayer = (TileLayer) world.getLayers().get("main");
        int tileId = tileLayer.getTileGrid()[column][row];
        return world.getTiles().get(tileId);
    }

    void stepX(World world, float secondsSinceLastUpdate) {
        Direction direction = actor.getDirection();

        Bounds collisionBounds = actor.getBounds();
        Vector2f collisionBoundsPosition = collisionBounds.getPosition();
        Vector2f nextCollisionBoundsPosition = collisionBoundsPosition.add(actor.getVelocity().multiply(secondsSinceLastUpdate));

        float collisionX = collisionBoundsPosition.getX();
        if (direction == Direction.RIGHT) {
            collisionX += collisionBounds.getWidth();
        }
        float nextCollisionX = nextCollisionBoundsPosition.getX();
        if (direction == Direction.RIGHT) {
            nextCollisionX += collisionBounds.getWidth();
        }
        float distanceToMoveX = nextCollisionX - collisionX;

        int topRow = ((int) collisionBoundsPosition.getY()) / world.getTileHeight();
        int bottomRow = (((int) collisionBoundsPosition.getY()) + collisionBounds.getHeight() - 1) / world.getTileHeight();
        int collisionColumn = ((int) collisionX) / world.getTileWidth();

        int closestTileColumn = direction == Direction.RIGHT ? world.getWidth() : 0;
        float distanceToClosestTile = 0;
        for (int row = topRow; row <= bottomRow; row++) {
            boolean foundSolidTile = false;
            if (direction == Direction.RIGHT) {
                for (int column = collisionColumn; column < world.getWidth(); column++) {
                    if (isSolidTile(column, row, world)) {
                        int tileX = column * world.getTileWidth();
                        if (column <= closestTileColumn) {
                            closestTileColumn = column;
                            float distanceToTile = tileX - collisionX;
                            if (distanceToTile > 0) {
                                distanceToClosestTile = distanceToTile;
                            } else {
                                distanceToClosestTile = 0;
                            }
                        }
                        foundSolidTile = true;
                        break;
                    }
                }
                if (!foundSolidTile) {
                    distanceToClosestTile = ((world.getWidth() - 1) * world.getTileWidth()) - collisionX;
                }
            } else {
                for (int column = collisionColumn; column >= 0; column--) {
                    if (isSolidTile(column, row, world)) {
                        int tileX = (column * world.getTileWidth()) + world.getTileWidth();
                        if (column >= closestTileColumn) {
                            closestTileColumn = column;
                            float distanceToTile = tileX - collisionX;
                            if (distanceToTile < 0) {
                                distanceToClosestTile = distanceToTile;
                            } else {
                                distanceToClosestTile = 0;
                            }
                        }
                        foundSolidTile = true;
                        break;
                    }
                }
                if (!foundSolidTile) {
                    distanceToClosestTile = collisionX;
                }
            }
        }

        distanceToMoveX = Math.min(Math.abs(distanceToMoveX), Math.abs(distanceToClosestTile));
        Vector2f distance = new Vector2f(distanceToMoveX, 0);
        if (direction == Direction.LEFT) {
            collisionBounds.setPosition(collisionBounds.getPosition().subtract(distance));
        } else {
            collisionBounds.setPosition(collisionBounds.getPosition().add(distance));
        }
    }

    void stepY(World world, float secondsSinceLastUpdate) {
        Vector2f velocity = actor.getVelocity();

        Bounds collisionBounds = actor.getBounds();
        Vector2f collisionBoundsPosition = collisionBounds.getPosition();
        Vector2f nextCollisionBoundsPosition = collisionBoundsPosition.add(velocity.multiply(secondsSinceLastUpdate));

        float topCollisionY = collisionBoundsPosition.getY();
        float bottomCollisionY = topCollisionY + collisionBounds.getHeight();
        float nextTopCollisionY = nextCollisionBoundsPosition.getY();
        float nextBottomCollisionY = nextTopCollisionY + collisionBounds.getHeight();
        float distanceToMoveY = nextTopCollisionY - topCollisionY;

        int topRow = ((int) collisionBoundsPosition.getY()) / world.getTileHeight();
        int bottomRow = (((int) collisionBoundsPosition.getY()) + collisionBounds.getHeight()) / world.getTileHeight();
        int leftColumn = ((int) collisionBoundsPosition.getX()) / world.getTileWidth();
        int rightColumn = (((int) collisionBoundsPosition.getX()) + collisionBounds.getWidth() - 1) / world.getTileWidth();

        int closestTileRow = actor.getVelocity().getY() >= 0 ? world.getHeight() : 0;
        float distanceToClosestTile = 0;
        for (int column = leftColumn; column <= rightColumn; column++) {
            if (actor.getVelocity().getY() >= 0) {
                for (int row = bottomRow; row < world.getHeight(); row++) {
                    if (isSolidTile(column, row, world)) {
                        int tileY = row * world.getTileHeight();
                        if (row < closestTileRow) {
                            closestTileRow = row;
                            float distanceToTile = tileY - bottomCollisionY;
                            if (distanceToTile > 0) {
                                distanceToClosestTile = distanceToTile;
                            } else {
                                distanceToClosestTile = 0;
                                actor.setVelocity(velocity.withY(0));
                            }
                        }
                        break;
                    }
                }
            } else {
                boolean foundSolidTile = false;
                for (int row = 0; row <= topRow; row++) {
                    if (isSolidTile(column, row, world)) {
                        int tileY = (row * world.getTileHeight()) + world.getTileHeight();
                        if (row >= closestTileRow) {
                            closestTileRow = row;
                            float distanceToTile = tileY - topCollisionY;
                            if (distanceToTile < 0) {
                                distanceToClosestTile = distanceToTile;
                            } else {
                                distanceToClosestTile = 0;
                            }
                        }
                        foundSolidTile = true;
                        break;
                    }
                }
                if (!foundSolidTile) {
                    distanceToClosestTile = topCollisionY;
                }
            }
        }

        distanceToMoveY = Math.min(Math.abs(distanceToMoveY), Math.abs(distanceToClosestTile));
        Vector2f vector2f = new Vector2f(0, distanceToMoveY);
        if (actor.getVelocity().getY() < 0) {
            collisionBounds.setPosition(collisionBoundsPosition.subtract(vector2f));
        } else {
            collisionBounds.setPosition(collisionBoundsPosition.add(vector2f));
        }


    }

}
