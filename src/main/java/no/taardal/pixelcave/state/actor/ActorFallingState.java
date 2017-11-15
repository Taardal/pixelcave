package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2d;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActorFallingState<T extends Actor> extends ActorState<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorFallingState.class);
    private static final int GRAVITY = 500;
    private static final int TERMINAL_VELOCITY = 300;

    protected boolean falling;

    private Animation fallingAnimation;
    private Animation landingAnimation;

    public ActorFallingState(T actor, StateMachine stateMachine) {
        super(actor, stateMachine);
        fallingAnimation = getFallingActorAnimation();
        landingAnimation = getLandingActorAnimation();
    }

    @Override
    public Animation getAnimation() {
        if (falling) {
            return fallingAnimation;
        } else {
            return landingAnimation;
        }
    }

    @Override
    public void onEntry() {
        falling = true;
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        if (!falling && actor.getVelocity().getY() >= 0) {
            onLanded();
        } else {
            fall(secondsSinceLastUpdate);
        }
        super.update(secondsSinceLastUpdate);
    }

    @Override
    public void onExit() {
        fallingAnimation.reset();
        landingAnimation.reset();
    }

    @Override
    protected Animation getActorAnimation() {
        return null;
    }

    protected abstract Animation getFallingActorAnimation();

    protected abstract Animation getLandingActorAnimation();

    protected abstract void onLanded();

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
        if (tile.getDirection() == Direction.RIGHT) {
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
