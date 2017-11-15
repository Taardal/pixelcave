package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2d;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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
        super.update(secondsSinceLastUpdate);

        TileLayer tileLayer = (TileLayer) actor.getWorld().getLayers().get("main");
        Map<Integer, Tile> tiles = actor.getWorld().getTiles();
        int tileWidth = actor.getWorld().getTileWidth();
        int tileHeight = actor.getWorld().getTileHeight();

        distanceWalked = actor.getVelocity().multiply(secondsSinceLastUpdate);

        double delta;
        Vector2d nextPosition;
        if (actor.isFacingRight()) {
            nextPosition = actor.getPosition().add(distanceWalked);
            delta = nextPosition.getX() - actor.getPosition().getX();
        } else {
            nextPosition = actor.getPosition().subtract(distanceWalked);
            delta = actor.getPosition().getX() - nextPosition.getX();
        }

        int collisionX;
        if (actor.isFacingLeft()) {
            collisionX = actor.getBounds().getX() - (int) delta;
        } else {
            collisionX = actor.getBounds().getX() + actor.getBounds().getWidth() + (int) delta;
        }
        int collisionY = actor.getBounds().getY();

        int column = collisionX / tileWidth;
        int row = collisionY / tileHeight;

        if (isCollision(collisionX, collisionY)) {
            int x;
            if (actor.isFacingLeft()) {
                x = (column * tileWidth) + tileWidth - (actor.getBounds().getX() - actor.getX());
            } else {
                x = (column * tileWidth) - actor.getBounds().getWidth() - (actor.getBounds().getX() - actor.getX());
            }
            nextPosition = new Vector2d(x, nextPosition.getY());
        }

        actor.setPosition(nextPosition);

    }

    private boolean isCollision(int x, int y) {

        TileLayer tileLayer = (TileLayer) actor.getWorld().getLayers().get("main");
        Map<Integer, Tile> tiles = actor.getWorld().getTiles();
        int tileWidth = actor.getWorld().getTileWidth();
        int tileHeight = actor.getWorld().getTileHeight();

        int column = x / tileWidth;
        int row = y / tileHeight;
        int tileId = tileLayer.getTileGrid()[column][row];
        Tile tile = getTile(tileId);
        return tile != null && !tile.isSlope();
    }

    private Tile getTile(int tileId) {
        if (tileId != World.NO_TILE_ID) {
            Tile tile = actor.getWorld().getTiles().get(tileId);
            if (tile != null) {
                return tile;
            }
        }
        return null;
    }

    protected Vector2d getVelocity() {
        return new Vector2d(actor.getMovementSpeed(), actor.getVelocity().getY());
    }

}
