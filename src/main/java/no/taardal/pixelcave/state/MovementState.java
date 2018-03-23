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

    @Override
    public void onEntry() {

    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        actor.getAnimation().update();
    }

    @Override
    public void onExit() {
        actor.getAnimation().reset();
    }

    Vector2f checkHorizontalCollision(Vector2f nextPosition, World world) {
        if (nextPosition.getX() < 0) {
            return nextPosition.withX(0);
        } else {
            int nextLeftColumn = actor.getLeftColumn(nextPosition);
            if (isHorizontalCollision(nextLeftColumn, world)) {
                float x = (nextLeftColumn * world.getTileWidth()) + world.getTileWidth();
                nextPosition = nextPosition.withX(x);
                actor.setVelocity(actor.getVelocity().withX(0));
            }
            int nextRightColumn = actor.getRightColumn(nextPosition);
            if (isHorizontalCollision(nextRightColumn, world)) {
                float x = (nextRightColumn * world.getTileWidth()) - actor.getWidth();
                nextPosition = nextPosition.withX(x);
                actor.setVelocity(actor.getVelocity().withX(0));
            }
            return nextPosition;
        }
    }

    Vector2f checkVerticalCollision(Vector2f nextPosition, World world) {
        if (nextPosition.getY() < 0) {
            return nextPosition.withY(0);
        } else {
            int nextTopRow = actor.getTopRow(nextPosition);
            if (isVerticalCollision(nextTopRow, world)) {
                float y = (nextTopRow * world.getTileHeight()) + world.getTileHeight();
                nextPosition = nextPosition.withY(y);
                actor.setVelocity(actor.getVelocity().withY(0));
            }
            int nextBottomRow = actor.getBottomRow(nextPosition);
            if (isVerticalCollision(nextBottomRow, world)) {
                float y = (nextBottomRow * world.getTileHeight()) - actor.getHeight();
                nextPosition = nextPosition.withY(y);
                actor.setVelocity(actor.getVelocity().withY(0));
            }
            return nextPosition;
        }
    }

    boolean isHorizontalCollision(int column, World world) {
        for (int row = actor.getTopRow(); row <= actor.getBottomRow(); row++) {
            if (isSolidTile(column, row, world)) {
                return true;
            }
        }
        return false;
    }

    boolean isVerticalCollision(int row, World world) {
        for (int column = actor.getLeftColumn(); column <= actor.getRightColumn(); column++) {
            if (isSolidTile(column, row, world)) {
                return true;
            }
        }
        return false;
    }

    boolean isBottomCollision(Vector2f position, World world) {
        int bottomRow = actor.getBottomRow(position);
        return isVerticalCollision(bottomRow, world);
    }

    boolean isBottomCollision(World world) {
        return isVerticalCollision(actor.getBottomRow(), world);
    }

    boolean isSolidTile(int column, int row, World world) {
        Tile tile = getTile(column, row, world);
        return tile != null && !tile.isSlope();
    }

    Tile getTile(int column, int row, World world) {
        TileLayer tileLayer = (TileLayer) world.getLayers().get("main");
        int tileId = tileLayer.getTileGrid()[column][row];
        return world.getTiles().get(tileId);
    }

}
