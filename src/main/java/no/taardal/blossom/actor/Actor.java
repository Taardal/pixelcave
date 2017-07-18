package no.taardal.blossom.actor;

import no.taardal.blossom.actorstate.ActorState;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

public abstract class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    World world;
    Vector2d position;
    Vector2d velocity;
    Direction direction;
    Deque<ActorState> states;
    int health;
    int damage;
    boolean dead;

    private Actor() {
        states = new ArrayDeque<>();
        position = Vector2d.zero();
        velocity = Vector2d.zero();
        direction = Direction.EAST;
    }

    public Actor(World world) {
        this();
        this.world = world;
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }

    public int getWidth() {
        return getAnimation().getWidth();
    }

    public int getHeight() {
        return getAnimation().getHeight();
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Animation getAnimation() {
        return states.getFirst().getAnimation();
    }

    public Rectangle getBounds() {
        return states.getFirst().getBounds();
    }

    public abstract void onAttacked(Actor attacker);

    public void update(double secondsSinceLastUpdate) {
        states.getFirst().update(secondsSinceLastUpdate);
    }

    public void draw(Camera camera) {
        getAnimation().draw(this, camera);
        if (getBounds() != null) {
            camera.drawRectangle(getX(), getY(), getAnimation().getWidth(), getAnimation().getHeight(), Color.YELLOW);
        }
    }

    public void pushState(ActorState actorState) {
        actorState.onEntry();
        states.addFirst(actorState);
    }

    public void popState() {
        states.getFirst().onExit();
        states.removeFirst();
    }

    public void changeState(ActorState actorState) {
        popState();
        pushState(actorState);
    }
}
