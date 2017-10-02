package no.taardal.blossom.actor;

import no.taardal.blossom.bounds.Bounds;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.animation.Animation;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.statemachine.StateMachine;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    SpriteSheet spriteSheet;
    World world;
    StateMachine movementStateMachine;
    StateMachine combatStateMachine;
    List<Enemy> enemies;
    Bounds bounds;
    Vector2d position;
    Vector2d velocity;
    Direction direction;
    int health;
    int damage;
    int attackRange;
    int movementSpeed;
    boolean dead;

    private Actor() {
        movementStateMachine = new StateMachine();
        combatStateMachine = new StateMachine();
        enemies = new ArrayList<>();
        bounds = new Bounds();
        position = Vector2d.zero();
        velocity = Vector2d.zero();
        direction = Direction.EAST;
    }

    public Actor(SpriteSheet spriteSheet) {
        this();
        this.spriteSheet = spriteSheet;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
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

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2d velocity) {
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

    public void update(double secondsSinceLastUpdate) {
        movementStateMachine.update(secondsSinceLastUpdate);
        combatStateMachine.update(secondsSinceLastUpdate);
    }

    public void draw(Camera camera) {
        getCurrentAnimation().draw(this, camera);
    }

    public Animation getCurrentAnimation() {
        if (!combatStateMachine.isEmpty()) {
            return combatStateMachine.getCurrentState().getAnimation();
        } else {
            return movementStateMachine.getCurrentState().getAnimation();
        }
    }

    public abstract void onAttacked(Actor attacker);

}
