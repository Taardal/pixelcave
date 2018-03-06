package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Actor {

    static final int GRID_COLLISION_MARGIN = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    SpriteSheet spriteSheet;
    World world;
    Vector2f position;
    Vector2f boundsPosition;
    Vector2f velocity;
    Direction direction;
    Direction previousDirection;
    Animation animation;
    int movementSpeed;
    int boundsWidth;
    int boundsHeight;

    private Actor() {
    }

    public Actor(SpriteSheet spriteSheet, World world, Vector2f position, Vector2f velocity, Direction direction) {
        this();
        this.spriteSheet = spriteSheet;
        this.world = world;
        this.position = position;
        this.velocity = velocity;
        this.direction = direction;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public int getWidth() {
        return spriteSheet.getSpriteWidth();
    }

    public int getHeight() {
        return spriteSheet.getSpriteHeight();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public int getTopRow() {
        return getTopRow(boundsPosition);
    }

    public int getTopRow(Vector2f position) {
        return ((int) position.getY()) / world.getTileHeight();
    }

    public int getBottomRow() {
        return getBottomRow(boundsPosition);
    }

    public int getBottomRow(Vector2f position) {
        return (((int) position.getY()) + boundsHeight) / world.getTileHeight();
    }

    public int getLeftColumn() {
        return getLeftColumn(boundsPosition);
    }

    public int getLeftColumn(Vector2f position) {
        return ((int) position.getX()) / world.getTileWidth();
    }

    public int getRightColumn() {
        return getRightColumn(boundsPosition);
    }

    public int getRightColumn(Vector2f position) {
        return (((int) position.getX()) + boundsWidth) / world.getTileWidth();
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void draw(Camera camera) {
        getCurrentAnimation().draw(this, camera);
    }

    public Animation getCurrentAnimation() {
        return animation;
    }

    public abstract void onAttacked(Actor attacker);

    public boolean isInSlope() {
        return direction == Direction.UP_RIGHT
                || direction == Direction.UP_LEFT
                || direction == Direction.DOWN_RIGHT
                || direction == Direction.DOWN_LEFT;
    }

    public boolean isFacingLeft() {
        return direction == Direction.LEFT || direction == Direction.UP_LEFT || direction == Direction.DOWN_LEFT;
    }

    public boolean isFacingRight() {
        return direction == Direction.RIGHT || direction == Direction.UP_RIGHT || direction == Direction.DOWN_RIGHT;
    }

    public boolean wasFacingLeft() {
        return previousDirection == Direction.LEFT || previousDirection == Direction.UP_LEFT || previousDirection == Direction.DOWN_LEFT;
    }

    public boolean wasFacingRight() {
        return previousDirection == Direction.RIGHT || previousDirection == Direction.UP_RIGHT || previousDirection == Direction.DOWN_RIGHT;
    }

    public boolean hasTurned() {
        return hasTurnedLeft() || hasTurnedRight();
    }

    public boolean hasTurnedRight() {
        return isFacingRight() && wasFacingLeft();
    }

    public boolean hasTurnedLeft() {
        return isFacingLeft() && wasFacingRight();
    }

    public Direction getPreviousDirection() {
        return previousDirection;
    }

    public void setPreviousDirection(Direction previousDirection) {
        this.previousDirection = previousDirection;
    }

    public boolean isFalling() {
        return getVelocity().getY() != 0;
    }

    public boolean isRunning() {
        return getVelocity().getX() != 0 && getVelocity().getY() == 0;
    }
}
