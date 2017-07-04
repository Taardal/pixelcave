package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.state.actorstate.ActorState;
import no.taardal.blossom.state.actorstate.FallingActorState;
import no.taardal.blossom.vector.Vector2i;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    AnimatedSprite sprite;
    World world;
    ActorState actorState;
    Vector2i position;
    Direction direction;
    boolean falling;

    public Actor(AnimatedSprite sprite, World world) {
        this.sprite = sprite;
        this.world = world;
        falling = true;
        actorState = new FallingActorState(this, world);
        actorState.onEntry();
        position = new Vector2i(200, 100);
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public AnimatedSprite getAnimatedSprite() {
        return sprite;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public Vector2i getPosition() {
        return position;
    }

    public void setPosition(Vector2i position) {
        this.position = position;
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

}
