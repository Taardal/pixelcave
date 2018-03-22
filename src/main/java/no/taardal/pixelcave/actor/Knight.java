package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Knight extends Actor implements Player {

    private static final Logger LOGGER = LoggerFactory.getLogger(Knight.class);
    private static final int TERMINAL_VELOCITY = 300;
    private static final int JUMPING_VELOCITY = -200;
    private static final int MOVEMENT_SPEED = 100;

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

    public Knight(SpriteSheet spriteSheet, World world, Vector2f position, Vector2f velocity, Direction direction) {
        super(spriteSheet, world, position, velocity, direction);
        animation = spriteSheet.getAnimations().get(Animation.Type.IDLE);
    }

    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                setDirection(Direction.LEFT);
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                setDirection(Direction.RIGHT);
            }
            velocity = getVelocity().withX(direction == Direction.RIGHT ? MOVEMENT_SPEED : -MOVEMENT_SPEED);
            animation = spriteSheet.getAnimations().get(Animation.Type.RUN);
        } else {
            velocity = getVelocity().withX(0);
            animation = spriteSheet.getAnimations().get(Animation.Type.IDLE);
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT) && !isFalling()) {
            velocity = getVelocity().withY(JUMPING_VELOCITY);
        }
    }

    @Override
    public void update(float secondsSinceLastUpdate) {
        if (isFalling()) {
            float velocityY = getVelocity().getY() + (World.GRAVITY * secondsSinceLastUpdate);
            if (velocityY > TERMINAL_VELOCITY) {
                velocityY = TERMINAL_VELOCITY;
            }
            velocity = getVelocity().withY(velocityY);
        } else if (!isVerticalCollision(getBottomRow())) {
            velocity = getVelocity().withY(25);
        }

        Vector2f nextPosition = position.add(getVelocity().multiply(secondsSinceLastUpdate));
        nextPosition = checkHorizontalCollision(nextPosition);
        nextPosition = checkVerticalCollision(nextPosition);
        Vector2f distanceToMove = nextPosition.subtract(position);
        position = getPosition().add(distanceToMove);

        animation.update();
    }

    private Vector2f checkHorizontalCollision(Vector2f nextPosition) {
        if (nextPosition.getX() < 0) {
            return nextPosition.withX(0);
        } else {
            int nextLeftColumn = getLeftColumn(nextPosition);
            if (isHorizontalCollision(nextLeftColumn)) {
                float x = (nextLeftColumn * world.getTileWidth()) + world.getTileWidth();
                nextPosition = nextPosition.withX(x);
                velocity = getVelocity().withX(0);
            }
            int nextRightColumn = getRightColumn(nextPosition);
            if (isHorizontalCollision(nextRightColumn)) {
                float x = (nextRightColumn * world.getTileWidth()) - getWidth();
                nextPosition = nextPosition.withX(x);
                velocity = getVelocity().withX(0);
            }
            return nextPosition;
        }
    }

    private Vector2f checkVerticalCollision(Vector2f nextPosition) {
        if (nextPosition.getY() < 0) {
            return nextPosition.withY(0);
        } else {
            int nextTopRow = getTopRow(nextPosition);
            if (isVerticalCollision(nextTopRow)) {
                float y = (nextTopRow * world.getTileHeight()) + world.getTileHeight();
                nextPosition = nextPosition.withY(y);
                velocity = getVelocity().withY(0);
            }
            int nextBottomRow = getBottomRow(nextPosition);
            if (isVerticalCollision(nextBottomRow)) {
                float y = (nextBottomRow * world.getTileHeight()) - getHeight();
                nextPosition = nextPosition.withY(y);
                velocity = getVelocity().withY(0);
            }
            return nextPosition;
        }
    }

    private boolean isHorizontalCollision(int column) {
        for (int row = getTopRow(position); row <= getBottomRow(position); row++) {
            if (isSolidTile(column, row)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVerticalCollision(int row) {
        for (int column = getLeftColumn(position); column <= getRightColumn(position); column++) {
            if (isSolidTile(column, row)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSolidTile(int column, int row) {
        Tile tile = getTile(column, row);
        return tile != null && !tile.isSlope();
    }

    private Tile getTile(int column, int row) {
        TileLayer tileLayer = (TileLayer) world.getLayers().get("main");
        int tileId = tileLayer.getTileGrid()[column][row];
        return world.getTiles().get(tileId);
    }

    @Override
    public void draw(Camera camera) {
        camera.drawRectangle(getX(), getY(), getWidth(), getHeight(), Color.RED);
        Animation animation = getAnimation();
        if (animation != null) {
            if (direction == Direction.LEFT) {
                animation.drawFlippedHorizontally(this, camera);
            } else {
                super.draw(camera);
            }
        }
    }

}

