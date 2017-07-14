package no.taardal.blossom.actor;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.state.actorstate.ActorState;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    AnimatedSprite animatedSprite;
    World world;
    ActorState actorState;
    Vector2d position;
    Vector2d velocity;
    Direction direction;

    public Actor(World world) {
        this.world = world;
        position = new Vector2d(200, 100);
        velocity = Vector2d.zero();
    }

    public void update(double timeSinceLastUpdate) {
        ActorState actorState = this.actorState.update(timeSinceLastUpdate);
        if (actorState != null) {
            LOGGER.debug("New actor state [{}]", actorState.toString());
            actorState.onEntry();
            this.actorState = actorState;
        }
        animatedSprite.update();
    }

    public void draw(Camera camera) {
        animatedSprite.draw(getX(), getY(), direction, camera);
    }

    public boolean isOnGround() {
        int column = getX() / world.getTileWidth();
        int row = (getY() + getHeight()) / world.getTileHeight();
        int tileId = world.getLayers().get("main").getTileGrid()[column][row];
        return tileId != World.NO_TILE_ID;
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }

    public int getWidth() {
        return animatedSprite.getWidth();
    }

    public int getHeight() {
        return animatedSprite.getHeight();
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }

    public void setAnimatedSprite(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
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
