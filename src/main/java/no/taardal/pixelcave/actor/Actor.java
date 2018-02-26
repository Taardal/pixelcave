package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    SpriteSheet spriteSheet;
    World world;
    Bounds bounds;
    Vector2f position;
    Vector2f velocity;
    Direction direction;
    Direction previousDirection;
    int health;
    int damage;
    int attackRange;
    int movementSpeed;
    boolean dead;
    Animation animation;

    private Actor() {
    }

    public Actor(SpriteSheet spriteSheet, World world, Bounds bounds, Vector2f position, Vector2f velocity, Direction direction) {
        this();
        this.spriteSheet = spriteSheet;
        this.world = world;
        this.bounds = bounds;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void update(float secondsSinceLastUpdate) {

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
