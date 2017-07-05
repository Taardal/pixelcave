package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.state.actorstate.ActorState;
import no.taardal.blossom.state.actorstate.FallingActorState;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    AnimatedSprite sprite;
    World world;
    ActorState actorState;
    Vector2d position;
    Vector2d velocity;
    Direction direction;
    boolean falling;

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public Actor(AnimatedSprite sprite, World world) {
        this.sprite = sprite;
        this.world = world;
        position = new Vector2d(200, 100);
        velocity = Vector2d.zero();
        actorState = new FallingActorState(this, world);
        actorState.onEntry();
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public AnimatedSprite getAnimatedSprite() {
        return sprite;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    public void update(double timeSinceLastUpdate, Keyboard keyboard) {
        ActorState actorState = this.actorState.update(timeSinceLastUpdate);
        if (actorState != null) {
            LOGGER.debug("New actor state [{}]", actorState.toString());
            actorState.onEntry();
            this.actorState = actorState;
        }
        sprite.update();
    }

    public void draw(Camera camera) {
        sprite.draw(getX(), getY(), direction, camera);
    }

    public boolean isOnGround() {
        int column = getX() / world.getTileWidth();
        int row = (getY() + getHeight()) / world.getTileHeight();
        int tileId = world.getLayers().get("main").getTileGrid()[column][row];
        if (tileId != World.NO_TILE_ID) {
            Tile tile = world.getTiles().get(tileId);
            if (!tile.isSlope()) {
                return true;
            } else {
                int slopeCollisionX = getX() + (getWidth() / 2);
                int tileX = column * world.getTileWidth();
                int tileY = row * world.getTileHeight();
                int slopeY;
                if (tile.getDirection() == Direction.EAST) {
                    slopeY = (tileY + world.getTileHeight()) - (slopeCollisionX - tileX);
                } else {
                    slopeY = (tileY + world.getTileHeight()) - ((tileX + world.getTileWidth()) - slopeCollisionX);
                }
                return getY() + getHeight() >= slopeY;
            }
        }
        return false;
    }

}
