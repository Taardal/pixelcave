package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
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
    Vector2f velocity;
    Direction direction;
    Animation animation;

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
        return animation.getWidth();
    }

    public int getHeight() {
        return animation.getHeight();
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

    public float getRightX() {
        return getRightX(position);
    }

    public float getRightX(Vector2f position) {
        return position.getX() + getWidth() - 1;
    }

    public float getY() {
        return position.getY();
    }

    public float getBottomY() {
        return getBottomY(position);
    }

    public float getBottomY(Vector2f position) {
        return position.getY() + getHeight() - 1;
    }

    public int getTopRow() {
        return getTopRow(position);
    }

    public int getTopRow(Vector2f position) {
        return ((int) position.getY()) / world.getTileHeight();
    }

    public int getBottomRow() {
        return getBottomRow(position);
    }

    public int getBottomRow(Vector2f position) {
        return ((int) getBottomY(position)) / world.getTileHeight();
    }

    public int getLeftColumn() {
        return getLeftColumn(position);
    }

    public int getLeftColumn(Vector2f position) {
        return ((int) position.getX()) / world.getTileWidth();
    }

    public int getRightColumn() {
        return getRightColumn(position);
    }

    public int getRightColumn(Vector2f position) {
        return ((int) getRightX(position)) / world.getTileWidth();
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

    public Animation getAnimation() {
        return animation;
    }

    public boolean isFalling() {
        return getVelocity().getY() != 0;
    }

    public boolean isRunning() {
        return getVelocity().getX() != 0 && getVelocity().getY() == 0;
    }

    public void draw(Camera camera) {
        getAnimation().draw(this, camera);
    }
}
