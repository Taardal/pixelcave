package no.taardal.blossom.actor;

import no.taardal.blossom.actorstate.ActorState;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.SpriteSheet;
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
    SpriteSheet spriteSheet;
    Deque<ActorState> states;
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
        position = Vector2d.zero();
        velocity = Vector2d.zero();
        direction = Direction.EAST;
    }

    public Actor(World world, SpriteSheet spriteSheet) {
        this();
        this.world = world;
        this.spriteSheet = spriteSheet;
    }

    public abstract void onAttacked(Actor attacker);

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }

    public int getWidth() {
        return spriteSheet.getWidth();
    }

    public int getHeight() {
        return spriteSheet.getHeight();
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Animation getAnimation() {
        return getCurrentState().getAnimation();
    }

    public Rectangle getBounds() {
        return getCurrentState().getBounds();
    }

    public void update(double secondsSinceLastUpdate) {
        getCurrentState().update(secondsSinceLastUpdate);
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
        getCurrentState().onExit();
        states.removeFirst();
    }

    public void changeState(ActorState actorState) {
        popState();
        pushState(actorState);
    }

    private ActorState getCurrentState() {
        return states.getFirst();
    }

}
