package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.sprite.Sprite;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Knight extends Actor implements Player {

    public static final int SPRITE_WIDTH = 40;
    public static final int SPRITE_HEIGHT = 40;

    private static final Logger LOGGER = LoggerFactory.getLogger(Knight.class);
    private static final int TERMINAL_VELOCITY = 300;
    private static final int JUMPING_VELOCITY = -200;

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

    private Animation idleAnimation;
    private Animation runningAnimation;
    private Animation jumpingAnimation;

    public Knight(SpriteSheet spriteSheet, World world, Vector2f position, Vector2f velocity) {
        super(spriteSheet, world, position, velocity, Direction.RIGHT);
        movementSpeed = 100;
        boundsWidth = 19;
        boundsHeight = 30;
        idleAnimation = getIdleAnimation();
        runningAnimation = getRunningAnimation();
        jumpingAnimation = getJumpingAnimation();
    }

    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            setPreviousDirection(getDirection());
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                setDirection(Direction.LEFT);
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                setDirection(Direction.RIGHT);
            }
            velocity = getVelocity().withX(direction == Direction.RIGHT ? getMovementSpeed() : -getMovementSpeed());
            animation = runningAnimation;
        } else {
            velocity = getVelocity().withX(0);
            animation = idleAnimation;
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT) && !isFalling()) {
            velocity = getVelocity().withY(JUMPING_VELOCITY);
        }
    }

    @Override
    public void update(float secondsSinceLastUpdate) {
        boundsPosition = new Vector2f(getBoundsX(), getBoundsY());

        if (isFalling()) {
            float velocityY = getVelocity().getY() + (World.GRAVITY * secondsSinceLastUpdate);
            if (velocityY > TERMINAL_VELOCITY) {
                velocityY = TERMINAL_VELOCITY;
            }
            velocity = getVelocity().withY(velocityY);
        } else if (!isHorizontalCollision(getBottomRow())) {
            velocity = getVelocity().withY(25);
        }

        Vector2f nextBoundsPosition = boundsPosition.add(getVelocity().multiply(secondsSinceLastUpdate));
        nextBoundsPosition = checkHorizontalCollision(nextBoundsPosition);
        nextBoundsPosition = checkVerticalCollision(nextBoundsPosition);
        Vector2f distanceToMove = nextBoundsPosition.subtract(boundsPosition);
        position = getPosition().add(distanceToMove);
        boundsPosition = boundsPosition.add(distanceToMove);

        getCurrentAnimation().update();
    }

    private Vector2f checkHorizontalCollision(Vector2f nextBoundsPosition) {
        if (nextBoundsPosition.getX() < 0) {
            return nextBoundsPosition.withX(0);
        } else {
            int leftColumn = getLeftColumn(nextBoundsPosition);
            if (isHorizontalCollision(leftColumn)) {
                float x = (leftColumn * world.getTileWidth()) + world.getTileWidth();
                nextBoundsPosition = nextBoundsPosition.withX(x);
                velocity = getVelocity().withX(0);
            }
            int rightColumn = getRightColumn(nextBoundsPosition);
            if (isHorizontalCollision(rightColumn)) {
                float x = (rightColumn * world.getTileWidth()) - boundsWidth - GRID_COLLISION_MARGIN;
                nextBoundsPosition = nextBoundsPosition.withX(x);
                velocity = getVelocity().withX(0);
            }
            return nextBoundsPosition;
        }
    }

    private Vector2f checkVerticalCollision(Vector2f nextBoundsPosition) {
        if (nextBoundsPosition.getY() < 0) {
            return nextBoundsPosition.withY(0);
        } else {
            int topRow = getTopRow(nextBoundsPosition);
            if (isVerticalCollision(topRow)) {
                float y = (topRow * world.getTileHeight()) + world.getTileHeight();
                nextBoundsPosition = nextBoundsPosition.withY(y);
                velocity = getVelocity().withY(0);
            }
            int bottomRow = getBottomRow(nextBoundsPosition);
            if (isVerticalCollision(bottomRow)) {
                float y = (bottomRow * world.getTileHeight()) - boundsHeight - GRID_COLLISION_MARGIN;
                nextBoundsPosition = nextBoundsPosition.withY(y);
                velocity = getVelocity().withY(0);
            }
            return nextBoundsPosition;
        }
    }

    private boolean isHorizontalCollision(int column) {
        for (int row = getTopRow(); row <= getBottomRow(); row++) {
            if (isSolidTile(column, row)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVerticalCollision(int row) {
        for (int column = getLeftColumn(); column <= getRightColumn(); column++) {
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
        if (boundsPosition != null) {
            camera.drawRectangle(boundsPosition.getX(), boundsPosition.getY(), boundsWidth, boundsHeight, Color.CYAN);
        }
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

    private float getBoundsX() {
        int marginX = getCurrentAnimation().equals(runningAnimation) ? 10 : 5;
        if (getDirection() == Direction.RIGHT) {
            return getX() + marginX;
        } else {
            return getX() + getWidth() - boundsWidth - marginX;
        }
    }

    private float getBoundsY() {
        return (getY() + getHeight()) - boundsHeight - GRID_COLLISION_MARGIN;
    }

    private Animation getIdleAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = getSpriteSheet().getSprites()[i][0];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(10);
        return animation;
    }

    private Animation getRunningAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = getSpriteSheet().getSprites()[i][8];
        }
        return new Animation(sprites);
    }

    private Animation getJumpingAnimation() {
        Sprite[] sprites = new Sprite[6];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = getSpriteSheet().getSprites()[i][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

}

