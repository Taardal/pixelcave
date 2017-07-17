package no.taardal.blossom.actor;

import no.taardal.blossom.actorstate.ActorState;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;

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

    public Animation getAnimation() {
        return states.getFirst().getAnimation();
    }

    public void update(double secondsSinceLastUpdate) {
        states.getFirst().update(secondsSinceLastUpdate);
        //for (Iterator<ActorState> iterator = states.iterator(); iterator.hasNext(); ) {
        //iterator.next().update(secondsSinceLastUpdate);
        //}
    }

    public void draw(Camera camera) {
        getAnimation().draw(this, camera);
    }

    public void pushState(ActorState actorState) {
        actorState.onEntry();
        states.addFirst(actorState);
    }

    public void popState() {
        states.getFirst().onExit();
        states.removeFirst();
        if (!states.isEmpty()) {
            //states.getFirst().onEntry();
        }
    }

    public void changeState(ActorState actorState) {
        popState();
        pushState(actorState);
    }
}
