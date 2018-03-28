package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;

public abstract class MovementState<T extends Actor> implements ActorState {

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
            int nextRightColumn = (((int) position.getX())  + actor.getCollisionBounds().getWidth()) / world.getTileHeight();
            if (isHorizontalCollision(nextRightColumn, world)) {
                float x = (nextRightColumn * world.getTileWidth()) - actor.getCollisionBounds().getWidth();
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
            int nextBottomRow = (((int) nextPosition.getY())  + actor.getCollisionBounds().getHeight()) / world.getTileHeight();
            if (isVerticalCollision(nextBottomRow, world)) {
                float y = (nextBottomRow * world.getTileHeight()) - actor.getCollisionBounds().getHeight();
                nextPosition = nextPosition.withY(y);
                actor.setVelocity(actor.getVelocity().withY(0));
            }
            return nextPosition;
        }
    }

    boolean isHorizontalCollision(int column, World world) {
        int topRow = ((int) actor.getCollisionBounds().getY()) / world.getTileHeight();
        int bottomRow = ((int) actor.getCollisionBounds().getY() + actor.getCollisionBounds().getHeight()) / world.getTileHeight();
        for (int row = topRow; row <= bottomRow; row++) {
            if (isSolidTile(column, row, world)) {
                return true;
            }
        }
        return false;
    }

    boolean isVerticalCollision(int row, World world) {
        int leftColumn = ((int) actor.getCollisionBounds().getX()) / world.getTileWidth();
        int rightColumn = ((int) actor.getCollisionBounds().getX() + actor.getCollisionBounds().getWidth()) / world.getTileWidth();
        for (int column = leftColumn; column <= rightColumn; column++) {
            if (isSolidTile(column, row, world)) {
                return true;
            }
        }
        return false;
    }

    boolean isBottomCollision(World world) {
        int bottomRow = (((int) actor.getCollisionBounds().getY())  + actor.getCollisionBounds().getHeight()) / world.getTileHeight();
        return isVerticalCollision(bottomRow, world);
    }

    boolean isSolidTile(int column, int row, World world) {
        TileLayer tileLayer = (TileLayer) world.getLayers().get("main");
        int tileId = tileLayer.getTileGrid()[column][row];
        Tile tile = world.getTiles().get(tileId);
        return tile != null && !tile.isSlope();
    }

}
