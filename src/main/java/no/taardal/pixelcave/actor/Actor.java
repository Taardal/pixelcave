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

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    StateMachine stateMachine;
    SpriteSheet spriteSheet;
    Direction direction;
    Vector2f velocity;
    Bounds spriteBounds;
    Bounds collisionBounds;

    private Actor() {
        stateMachine = new StateMachine();
    }

    public Actor(SpriteSheet spriteSheet, Direction direction, Vector2f velocity, Vector2f position) {
        this();
        this.spriteSheet = spriteSheet;
        this.direction = direction;
        this.velocity = velocity;
        spriteBounds = createSpriteBounds(spriteSheet, position);
        collisionBounds = new Bounds.Builder().copy(spriteBounds).build();
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

    public Bounds getSpriteBounds() {
        return spriteBounds;
    }

    public void setSpriteBounds(Bounds spriteBounds) {
        this.spriteBounds = spriteBounds;
    }

    public Bounds getCollisionBounds() {
        return collisionBounds;
    }

    public void setCollisionBounds(Bounds collisionBounds) {
        this.collisionBounds = collisionBounds;
    }

    public int getWidth() {
        return spriteBounds.getWidth();
    }

    public int getHeight() {
        return spriteBounds.getHeight();
    }

    public Vector2f getPosition() {
        return spriteBounds.getPosition();
    }

    public void setPosition(Vector2f position) {
        spriteBounds.setPosition(position);
    }

    public float getX() {
        return spriteBounds.getPosition().getX();
    }

    public float getY() {
        return spriteBounds.getPosition().getY();
    }

    public Animation getAnimation() {
        return stateMachine.isEmpty() ? null : stateMachine.getCurrentState().getAnimation();
    }

    public void draw(Camera camera) {
        Animation animation = getAnimation();
        if (animation != null) {
            animation.draw(this, camera);
        }
        camera.drawRectangle(spriteBounds.getX(), spriteBounds.getY(), spriteBounds.getWidth(), spriteBounds.getHeight(), Color.RED);
        camera.drawRectangle(collisionBounds.getX(), collisionBounds.getY(), collisionBounds.getWidth(), collisionBounds.getHeight(), Color.CYAN);
    }

    private Bounds createSpriteBounds(SpriteSheet spriteSheet, Vector2f position) {
        int largestSpriteWidth = 0;
        int largestSpriteHeight = 0;
        for (int i = 0; i < spriteSheet.getSprites().length; i++) {
            for (int j = 0; j < spriteSheet.getSprites()[0].length; j++) {
                BufferedImage sprite = spriteSheet.getSprites()[i][j];
                if (sprite != null) {
                    largestSpriteWidth = sprite.getWidth() > largestSpriteWidth ? sprite.getWidth() : largestSpriteWidth;
                    largestSpriteHeight = sprite.getHeight() > largestSpriteHeight ? sprite.getHeight() : largestSpriteHeight;
                }
            }
        }
        return new Bounds.Builder().setPosition(position).setWidth(largestSpriteWidth).setHeight(largestSpriteHeight).build();
    }

}
