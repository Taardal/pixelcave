package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.vector.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Knight extends Actor implements Player {

    private static final Logger LOGGER = LoggerFactory.getLogger(Knight.class);

    public enum Theme {

        BLACK,
        BLUE,
        ELDER,
        GOLD,
        GREY,
        HOLY,
        RED,
        WHITE,
        YELLOW,
        YOUNG

    }

    public Knight(SpriteSheet spriteSheet) {
        super(spriteSheet);
        velocity = Vector2f.zero();
        direction = Direction.RIGHT;

        health = 100;
        damage = 50;
        attackRange = 20;
        movementSpeed = 100;
    }

    @Override
    public void update(float secondsSinceLastUpdate) {
        /*

        Update values (bounds size, velocity)
        --------------------------------------------------------
        if (actor.isFalling()) {
            actor.setVelocity(getFallingVelocity(secondsSinceLastUpdate));
        }

        Calculate next (bounds) position
        --------------------------------------------------------
        Vector2f distanceToMove = actor.getVelocity().multiply(secondsSinceLastUpdate);
        Vector2f nextBoundsPosition = actor.getBounds().getPosition().add(distanceToMove);
        if (nextBoundsPosition.getX() < 0) {
            nextBoundsPosition = new Vector2f(0, nextBoundsPosition.getY());
        }
        if (nextBoundsPosition.getY() < 0) {
            nextBoundsPosition = new Vector2f(nextBoundsPosition.getX(), 0);
        }

        Check, and adjust for, collisions
        --------------------------------------------------------
        nextBoundsPosition = checkCollision(nextBoundsPosition);

        Set new position
        --------------------------------------------------------
        distanceToMove = nextBoundsPosition.subtract(actor.getBounds().getPosition());
        actor.setPosition(actor.getPosition().add(distanceToMove));
        actor.getBounds().setPosition(actor.getBounds().getPosition().add(distanceToMove));

        Set animation if needed
        --------------------------------------------------------


        Update animation
        --------------------------------------------------------
        getCurrentAnimation().update();

        */
    }

    @Override
    public void draw(Camera camera) {
        camera.drawRectangle(getX(), getY(), getWidth(), getHeight(), Color.RED);
        camera.drawRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), Color.CYAN);

        Animation animation = getCurrentAnimation();
        if (animation != null) {
            if (isFacingLeft()) {
                animation.drawFlippedHorizontally(this, camera);
            } else {
                super.draw(camera);
            }
        }
    }

    @Override
    public void onAttacked(Actor attacker) {

    }

    public void handleInput(Keyboard keyboard) {
        movementStateMachine.handleInput(keyboard);
    }

}

