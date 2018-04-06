package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    StateMachine stateMachine;
    SpriteSheet spriteSheet;
    Direction direction;
    Vector2f velocity;
    Vector2f position;
    Map<Animation.Type, Animation> animations;
    int width;
    int height;
    int movementSpeed;

    private Actor() {
        stateMachine = new StateMachine();
    }

    public Actor(SpriteSheet spriteSheet, Direction direction, Vector2f velocity, Vector2f position) {
        this();
        this.spriteSheet = spriteSheet;
        this.direction = direction;
        this.velocity = velocity;
        this.position = position;
        animations = createAnimations();
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Map<Animation.Type, Animation> getAnimations() {
        return animations;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void handleInput(Keyboard keyboard) {
        if (!stateMachine.isEmpty()) {
            stateMachine.getCurrentState().handleInput(keyboard);
        } else {
            LOGGER.warn("Could not handle input. State machine was empty.");
        }
    }

    public void update(World world, float secondsSinceLastUpdate) {
        if (!stateMachine.isEmpty()) {
            stateMachine.getCurrentState().update(world, secondsSinceLastUpdate);
        } else {
            LOGGER.warn("Could not update. State machine was empty.");
        }
    }

    public void draw(Camera camera) {
        Animation animation = stateMachine.isEmpty() ? null : stateMachine.getCurrentState().getAnimation();
        if (animation != null) {
            animation.draw(this, camera, isFlipped());
        } else {
            LOGGER.warn("Could not draw. Animation was null.");
        }
    }

    abstract Map<Animation.Type,Animation> createAnimations();

    abstract boolean isFlipped();

}
