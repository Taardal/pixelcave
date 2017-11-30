package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.actor.Actor;
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

        float leftX = nextBoundsPosition.getX();
        float rightX = leftX + actor.getBounds().getWidth();
        float topY = nextBoundsPosition.getY();
        float bottomY = topY + actor.getBounds().getHeight();

        int leftColumn = ((int) leftX) / actor.getWorld().getTileWidth();
        int rightColumn = ((int) rightX) / actor.getWorld().getTileWidth();
        int topRow = ((int) topY) / actor.getWorld().getTileHeight();
        int bottomRow = ((int) bottomY) / actor.getWorld().getTileHeight();

        float x = nextBoundsPosition.getX();
        float y = nextBoundsPosition.getY();
        if (isSolidTile(leftColumn, bottomRow - 1)) {
            x = (leftColumn * actor.getWorld().getTileWidth()) + actor.getWorld().getTileWidth();
        }
        if (isSolidTile(rightColumn, bottomRow - 1)) {
            x = (rightColumn * actor.getWorld().getTileWidth()) - actor.getBounds().getWidth();
        }
        if (isSolidTile(leftColumn + 1, topRow)) {
            y = (topRow * actor.getWorld().getTileHeight()) + actor.getWorld().getTileHeight();
        }
        if (isSolidTile(leftColumn + 1, bottomRow)) {
            y = (bottomRow * actor.getWorld().getTileHeight()) - actor.getBounds().getHeight();
            actor.setVelocity(new Vector2f(actor.getVelocity().getX(), 0));
        }
        nextBoundsPosition = new Vector2f(x, y);

        distanceToMove = nextBoundsPosition.subtract(actor.getBounds().getPosition());
        actor.setPosition(actor.getPosition().add(distanceToMove));
        actor.getBounds().setPosition(actor.getBounds().getPosition().add(distanceToMove));

        super.update(secondsSinceLastUpdate);
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
