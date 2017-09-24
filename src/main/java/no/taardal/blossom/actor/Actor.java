package no.taardal.blossom.actor;

import no.taardal.blossom.bounds.Bounds;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.state.actor.ActorState;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    SpriteSheet spriteSheet;
    World world;
    Deque<ActorState> states;
    Map<String, Animation> animations;
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
        states = new ArrayDeque<>();
        animations = new HashMap<>();
        enemies = new ArrayList<>();
        bounds = new Bounds();
        position = Vector2d.zero();
        velocity = Vector2d.zero();
        direction = Direction.EAST;
    }

    protected abstract Map<String, Animation> createAnimations();

    public Actor(SpriteSheet spriteSheet) {
        this();
        this.spriteSheet = spriteSheet;
        animations = createAnimations();
    }

    public Map<String, Animation> getAnimations() {
        return animations;
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

    public Animation getCurrentAnimation() {
        return getCurrentState().getAnimation();
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void update(double secondsSinceLastUpdate) {
        getCurrentState().update(secondsSinceLastUpdate);
    }

    public void draw(Camera camera) {
        getCurrentAnimation().draw(this, camera);
        if (getBounds() != null) {
            camera.drawRectangle(getX(), getY(), getCurrentAnimation().getWidth(), getCurrentAnimation().getHeight(), Color.YELLOW);
        }
    }

    public void pushState(ActorState actorState) {
        actorState.onEntry();
        states.addFirst(actorState);
    }

    public void popState() {
        getCurrentState().onExit();
        states.removeFirst();
    }

    public void changeState(ActorState actorState) {
        popState();
        pushState(actorState);
    }

    public abstract void onAttacked(Actor attacker);

    private ActorState getCurrentState() {
        return states.getFirst();
    }

}
