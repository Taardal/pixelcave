package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.tile.Tile;
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

    private boolean isSolidTile(int column, int row, World world) {
        TileLayer tileLayer = (TileLayer) world.getLayers().get("main");
        int tileId = tileLayer.getTileGrid()[column][row];
        Tile tile = world.getTiles().get(tileId);
        return tile != null && !tile.isSlope();
    }

}
