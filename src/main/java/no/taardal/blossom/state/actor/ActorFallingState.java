package no.taardal.blossom.state.actor;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.layer.TileLayer;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActorFallingState<T extends Actor> implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorFallingState.class);
    private static final int GRAVITY = 500;
    private static final int TERMINAL_VELOCITY = 300;

    protected T actor;
    protected boolean falling;

    public ActorFallingState(T actor) {
        this.actor = actor;
    }

    @Override
    public Animation getAnimation() {
        if (falling) {
            return actor.getAnimations().get("FALLING");
        } else {
            return actor.getAnimations().get("LANDING");
        }
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
        actor.getAnimations().get("FALLING").reset();
        actor.getAnimations().get("LANDING").reset();
    }

    protected abstract void onLanded();

    protected abstract void updateBounds();

    private void fall(double secondsSinceLastUpdate) {
        actor.setPosition(getNextPosition(secondsSinceLastUpdate));
        actor.setVelocity(getNextVelocity(secondsSinceLastUpdate));
    }

    private Vector2d getNextPosition(double secondsSinceLastUpdate) {
        Vector2d distance = actor.getVelocity().multiply(secondsSinceLastUpdate);
        Vector2d nextPosition = actor.getPosition().add(distance);
        int column = (int) nextPosition.getX() / actor.getWorld().getTileWidth();
        int actorBottomY = (int) nextPosition.getY() + actor.getHeight();
        int row = actorBottomY / actor.getWorld().getTileHeight();
        int tileId = getMainTileLayer().getTileGrid()[column][row];
        if (tileId != World.NO_TILE_ID) {
            Tile tile = actor.getWorld().getTiles().get(tileId);
            if (tile.isSlope()) {
                int slopeY = getSlopeY(column, row, tile);
                if (actorBottomY > slopeY) {
                    int y = slopeY - actor.getHeight();
                    nextPosition = new Vector2d(nextPosition.getX(), y);
                    falling = false;
                }
            } else {
                int y = (row * actor.getWorld().getTileHeight()) - actor.getHeight();
                nextPosition = new Vector2d(nextPosition.getX(), y);
                falling = false;
            }
        }
        return nextPosition;
    }

    private TileLayer getMainTileLayer() {
        return (TileLayer) actor.getWorld().getLayers().get("main");
    }

    private int getSlopeY(int column, int row, Tile tile) {
        int slopeCollisionX = actor.getX() + (actor.getWidth() / 2);
        int tileX = column * actor.getWorld().getTileWidth();
        int tileY = row * actor.getWorld().getTileHeight();
        if (tile.getDirection() == Direction.EAST) {
            return (tileY + actor.getWorld().getTileHeight()) - (slopeCollisionX - tileX);
        } else {
            return (tileY + actor.getWorld().getTileHeight()) - ((tileX + actor.getWorld().getTileWidth()) - slopeCollisionX);
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
        int column = actor.getX() / actor.getWorld().getTileWidth();
        int row = (actor.getY() + actor.getHeight()) / actor.getWorld().getTileHeight();
        int tileId = getMainTileLayer().getTileGrid()[column][row];
        return tileId != World.NO_TILE_ID;
    }

}
