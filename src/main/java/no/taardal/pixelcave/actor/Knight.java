package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
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
    private static final int GRID_COLLISION_MARGIN = 1;

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

    public Knight(SpriteSheet spriteSheet, World world, Vector2f position, Vector2f velocity) {
        super(spriteSheet, world, new Bounds(), position, velocity, Direction.RIGHT);

        health = 100;
        damage = 50;
        attackRange = 20;
        movementSpeed = 100;

        animation = getIdleAnimation();

        getBounds().setWidth(19);
        getBounds().setHeight(30);

        float boundsX;
        int marginX = 5;
        if (getDirection() == Direction.RIGHT) {
            boundsX = getX() + marginX;
        } else {
            boundsX = getX() + getWidth() - getBounds().getWidth() - marginX;
        }
        float boundsY = (getY() + getHeight()) - getBounds().getHeight() - GRID_COLLISION_MARGIN;
        getBounds().setPosition(new Vector2f(boundsX, boundsY));
    }

    private Animation getRunningAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = getSpriteSheet().getSprites()[i][8];
        }
        return new Animation(sprites);
    }

    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            animation = getRunningAnimation();
            getBounds().setWidth(27);
            getBounds().setHeight(27);
            setPreviousDirection(getDirection());
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                if (getDirection() == Direction.UP_RIGHT || getDirection() == Direction.DOWN_LEFT) {
                    setDirection(Direction.DOWN_LEFT);
                } else if (getDirection() == Direction.DOWN_RIGHT || getDirection() == Direction.UP_LEFT) {
                    setDirection(Direction.UP_LEFT);
                } else {
                    setDirection(Direction.LEFT);
                }
                setVelocity(new Vector2f(-getMovementSpeed(), getVelocity().getY()));
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                if (getDirection() == Direction.UP_LEFT) {
                    setDirection(Direction.DOWN_RIGHT);
                } else if (getDirection() == Direction.DOWN_LEFT) {
                    setDirection(Direction.UP_RIGHT);
                } else {
                    if (getDirection() != Direction.DOWN_RIGHT && getDirection() != Direction.UP_RIGHT) {
                        setDirection(Direction.RIGHT);
                    }
                }
                setVelocity(new Vector2f(getMovementSpeed(), getVelocity().getY()));
            }
        } else {
            getBounds().setWidth(19);
            getBounds().setHeight(30);
            setVelocity(getVelocity().withX(0));
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT) && getVelocity().getY() == 0) {
            setVelocity(getVelocity().withY(-200));
        }
    }

    @Override
    public void update(float secondsSinceLastUpdate) {
        if (isFalling()) {
            float velocityY = getVelocity().getY() + (World.GRAVITY * secondsSinceLastUpdate);
            if (velocityY > TERMINAL_VELOCITY) {
                velocityY = TERMINAL_VELOCITY;
            }
            setVelocity(getVelocity().withY(velocityY));
        }

        Vector2f distanceToMove = getVelocity().multiply(secondsSinceLastUpdate);
        Vector2f nextBoundsPosition = getBounds().getPosition().add(distanceToMove);
        if (nextBoundsPosition.getX() < 0) {
            nextBoundsPosition = nextBoundsPosition.withX(0);
        }
        if (nextBoundsPosition.getY() < 0) {
            nextBoundsPosition = nextBoundsPosition.withY(0);
        }

        int topRow = ((int) getBounds().getY()) / world.getTileHeight();
        int bottomRow = (((int) getBounds().getY()) + getBounds().getHeight()) / world.getTileHeight();
        int column = ((int) nextBoundsPosition.getX()) / world.getTileWidth();
        for (int i = topRow; i <= bottomRow; i++) {
            if (isSolidTile(column, i)) {
                float x = (column * getWorld().getTileWidth()) + getWorld().getTileWidth();
                nextBoundsPosition = nextBoundsPosition.withX(x);
                setVelocity(getVelocity().withX(0));
                break;
            }
        }
        column = (((int) nextBoundsPosition.getX()) + getBounds().getWidth()) / world.getTileWidth();
        for (int i = topRow; i <= bottomRow; i++) {
            if (isSolidTile(column, i)) {
                float x = (column * getWorld().getTileWidth()) - getBounds().getWidth() - GRID_COLLISION_MARGIN;
                nextBoundsPosition = nextBoundsPosition.withX(x);
                setVelocity(getVelocity().withX(0));
                break;
            }
        }
        distanceToMove = nextBoundsPosition.subtract(getBounds().getPosition());
        setPosition(getPosition().add(distanceToMove));
        getBounds().setPosition(nextBoundsPosition);

        int leftColumn = ((int) getBounds().getX()) / world.getTileWidth();
        int rightColumn = (((int) getBounds().getX()) + getBounds().getWidth()) / world.getTileWidth();
        if (velocity.getY() < 0) {
            int row = ((int) nextBoundsPosition.getY()) / world.getTileHeight();
            for (int i = leftColumn; i <= rightColumn; i++) {
                if (isSolidTile(i, row)) {
                    float y = (row * getWorld().getTileHeight()) + getWorld().getTileHeight();
                    nextBoundsPosition = nextBoundsPosition.withY(y);
                    setVelocity(getVelocity().withY(0));
                    break;
                }
            }
        } else {
            int row = (((int) nextBoundsPosition.getY()) + getBounds().getHeight()) / world.getTileHeight();
            for (int i = leftColumn; i <= rightColumn; i++) {
                if (isSolidTile(i, row)) {
                    float y = (row * getWorld().getTileHeight()) - getBounds().getHeight() - GRID_COLLISION_MARGIN;
                    nextBoundsPosition = nextBoundsPosition.withY(y);
                    setVelocity(getVelocity().withY(0));
                    break;
                }
            }
        }
        distanceToMove = nextBoundsPosition.subtract(getBounds().getPosition());
        setPosition(getPosition().add(distanceToMove));
        getBounds().setPosition(nextBoundsPosition);

        getCurrentAnimation().update();
    }

    protected boolean isSolidTile(int column, int row) {
        Tile tile = getTile(column, row);
        return tile != null && !tile.isSlope();
    }

    protected Tile getTile(int column, int row) {
        TileLayer tileLayer = (TileLayer) getWorld().getLayers().get("main");
        int tileId = tileLayer.getTileGrid()[column][row];
        return getTile(tileId);
    }

    protected Tile getTile(int tileId) {
        if (tileId != World.NO_TILE_ID) {
            Tile tile = getWorld().getTiles().get(tileId);
            if (tile != null) {
                return tile;
            }
        }
        return null;
    }

    @Override
    public void draw(Camera camera) {
        camera.drawRectangle(getX(), getY(), getWidth(), getHeight(), Color.RED);
        if (bounds != null) {
            camera.drawRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), Color.CYAN);
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

    private Animation getIdleAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = getSpriteSheet().getSprites()[i][0];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(10);
        return animation;
    }

    private Animation getJumpingActorAnimation() {
        Sprite[] sprites = new Sprite[6];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = getSpriteSheet().getSprites()[i][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

}

