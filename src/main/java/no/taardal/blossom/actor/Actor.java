package no.taardal.blossom.actor;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.state.actorstate.ActorState;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    World world;
    Vector2d position;
    Vector2d velocity;
    Direction direction;
    Deque<ActorState> states;

    private Actor() {
        states = new ArrayDeque<>();
    }

    public Actor(World world) {
        this();
        this.world = world;
    }

    public void update(double secondsSinceLastUpdate) {
        for (Iterator<ActorState> iterator = states.iterator(); iterator.hasNext(); ) {
            iterator.next().update(secondsSinceLastUpdate);
        }
    }

    public void draw(Camera camera) {
        states.getFirst().draw(camera);
    }

    public void pushState(ActorState actorState) {
        actorState.onEntry();
        states.addFirst(actorState);
    }

    public void popState() {
        states.getFirst().onExit();
        states.removeFirst();
        if (!states.isEmpty()) {
            states.getFirst().onEntry();
        }
    }

    public void changeState(ActorState actorState) {
        popState();
        pushState(actorState);
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }

    public int getWidth() {
        return states.getFirst().getAnimation().getWidth();
    }

    public int getHeight() {
        return states.getFirst().getAnimation().getHeight();
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
}
