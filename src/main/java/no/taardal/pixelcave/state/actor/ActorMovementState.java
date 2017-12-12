package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActorMovementState<T extends Actor> extends ActorState<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorMovementState.class);
    private static final int GRAVITY = 500;
    private static final int TERMINAL_VELOCITY = 300;

    public ActorMovementState(T actor, StateMachine stateMachine) {
        super(actor, stateMachine);
    }

    @Override
    public void update(float secondsSinceLastUpdate) {
        if (actor.isFalling()) {
            actor.setVelocity(getFallingVelocity(secondsSinceLastUpdate));
        }
        Vector2f distanceToMove = actor.getVelocity().multiply(secondsSinceLastUpdate);

        Vector2f nextBoundsPosition = actor.getBounds().getPosition().add(distanceToMove);
        if (nextBoundsPosition.getX() < 0) {
            nextBoundsPosition = new Vector2f(0, nextBoundsPosition.getY());
        }
        if (nextBoundsPosition.getY() < 0) {
            nextBoundsPosition = new Vector2f(nextBoundsPosition.getX(), 0);
        }

        nextBoundsPosition = checkCollision(nextBoundsPosition);

        distanceToMove = nextBoundsPosition.subtract(actor.getBounds().getPosition());
        actor.setPosition(actor.getPosition().add(distanceToMove));
        actor.getBounds().setPosition(actor.getBounds().getPosition().add(distanceToMove));

        super.update(secondsSinceLastUpdate);
    }

    private Vector2f checkCollision(Vector2f position) {

        if (actor.getVelocity().getY() < 0) {
            position = checkTopCollision(position);
        } else {
            position = checkBotCollision(position);
        }
        if (actor.getDirection() == Direction.LEFT) {
            position = checkLeftCollision(position);
        } else if (actor.getVelocity().getX() > 0) {
            position = checkRightCollision(position);
        }
        return position;
    }

    private Vector2f checkTopCollision(Vector2f boundsPosition) {
        int startX = (int) boundsPosition.getX() + 1;
        int endX = startX + actor.getBounds().getWidth() - 1;
        int row = ((int) boundsPosition.getY()) / actor.getWorld().getTileHeight();
        for (int x = startX; x <= endX; x++) {
            int column = x / actor.getWorld().getTileWidth();
            if (isSolidTile(column, row)) {
                float y = (row * actor.getWorld().getTileHeight()) + actor.getWorld().getTileHeight();
                boundsPosition = boundsPosition.withY(y);
                break;
            }
        }
        return boundsPosition;
    }

    private Vector2f checkBotCollision(Vector2f boundsPosition) {
        int startX = ((int) boundsPosition.getX()) + 1;
        int endX = startX + actor.getBounds().getWidth() - 1;
        int bottomY = ((int) boundsPosition.getY()) + actor.getBounds().getHeight();
        int row = bottomY / actor.getWorld().getTileHeight();

        boolean collision = false;
        for (int x = startX; x <= endX; x++) {
            int column = x / actor.getWorld().getTileWidth();
            if (isSolidTile(column, row)) {
                float y = (row * actor.getWorld().getTileHeight()) - actor.getBounds().getHeight();
                boundsPosition = boundsPosition.withY(y);
                actor.setVelocity(actor.getVelocity().withY(0));
                collision = true;
                break;
            }
        }
        if (!collision && !actor.isFalling()) {
            actor.setVelocity(actor.getVelocity().withY(50));
        }
        return boundsPosition;
    }

    private Vector2f checkLeftCollision(Vector2f boundsPosition) {
        int startY = (int) boundsPosition.getY() + 1;
        int endY = startY + actor.getBounds().getHeight() - 1;
        int column = ((int) boundsPosition.getX()) / actor.getWorld().getTileWidth();
        for (int y = startY; y < endY; y++) {
            int row = y / actor.getWorld().getTileHeight();
            if (isSolidTile(column, row)) {
                float x = (column * actor.getWorld().getTileWidth()) + actor.getWorld().getTileWidth();
                boundsPosition = boundsPosition.withX(x);
                actor.setVelocity(actor.getVelocity().withX(0));
                break;
            }
        }
        return boundsPosition;
    }

    private Vector2f checkRightCollision(Vector2f boundsPosition) {
        int startY = (int) boundsPosition.getY() + 1;
        int endY = startY + actor.getBounds().getHeight() - 1;
        int column = ((int) boundsPosition.getX() + actor.getBounds().getWidth()) / actor.getWorld().getTileWidth();
        for (int y = startY; y < endY; y++) {
            int row = y / actor.getWorld().getTileHeight();
            if (isSolidTile(column, row)) {
                float x = (column * actor.getWorld().getTileWidth()) - actor.getBounds().getWidth();
                boundsPosition = boundsPosition.withX(x);
                actor.setVelocity(actor.getVelocity().withX(0));
                break;
            }
        }
        return boundsPosition;
    }

    protected boolean isSolidTile(int column, int row) {
        Tile tile = getTile(column, row);
        return tile != null && !tile.isSlope();
    }

    protected Tile getTile(int column, int row) {
        TileLayer tileLayer = (TileLayer) actor.getWorld().getLayers().get("main");
        int tileId = tileLayer.getTileGrid()[column][row];
        return getTile(tileId);
    }

    protected Tile getTile(int tileId) {
        if (tileId != World.NO_TILE_ID) {
            Tile tile = actor.getWorld().getTiles().get(tileId);
            if (tile != null) {
                return tile;
            }
        }
        return null;
    }

    protected Vector2f getFallingVelocity(float secondsSinceLastUpdate) {
        float velocityY = actor.getVelocity().getY() + (GRAVITY * secondsSinceLastUpdate);
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
        return new Vector2f(actor.getVelocity().getX(), velocityY);
    }

}
