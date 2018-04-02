package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.statemachine.StateMachine;
import no.taardal.pixelcave.vector.Vector2f;
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
    Bounds bounds;

    private Actor() {
        stateMachine = new StateMachine();
    }

    public Actor(SpriteSheet spriteSheet, Direction direction, Vector2f velocity, Vector2f position) {
        this();
        this.spriteSheet = spriteSheet;
        this.direction = direction;
        this.velocity = velocity;
        this.position = position;
        bounds = new Bounds.Builder().setPosition(position).build();
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
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

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public int getWidth() {
        return bounds.getWidth();
    }

    public int getHeight() {
        return bounds.getHeight();
    }

    public Vector2f getPosition() {
        return bounds.getPosition();
    }

    public void setPosition(Vector2f position) {
        this.position = position;
        bounds.setPosition(position);
    }

    public float getX() {
        return bounds.getPosition().getX();
    }

    public float getY() {
        return bounds.getPosition().getY();
    }

    public Animation getAnimation() {
        return stateMachine.isEmpty() ? null : stateMachine.getCurrentState().getAnimation();
    }

    public Map<Animation.Type, Animation> getAnimations() {
        return spriteSheet.getAnimations();
    }

    public int getMovementSpeed() {
        return 100;
    }

    public void draw(Camera camera) {
        Animation animation = getAnimation();
        if (animation != null) {
            animation.draw(this, camera);
        }
    }

}
