package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Key;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Player extends Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    private Direction direction;

    public Player(AnimatedSprite animatedSprite, TiledEditorMap tiledEditorMap) {
        super(animatedSprite, tiledEditorMap);
        direction = Direction.EAST;

        velocityX = 3;
        velocityY = 1;
        y = 130;
        x = 250;

        Rectangle boundingBox = new Rectangle();
        int boundingBoxWidth = (animatedSprite.getWidth() / tiledEditorMap.getTileWidth()) * tiledEditorMap.getTileWidth();
        int boundingBoxHeight = (animatedSprite.getHeight() / tiledEditorMap.getTileHeight()) * tiledEditorMap.getTileHeight();
        LOGGER.debug("Bounding box: [{}w, {}h]", boundingBoxWidth, boundingBoxHeight);
        boundingBox.setBounds(x, y, boundingBoxWidth, boundingBoxHeight);
        setBoundingBox(boundingBox);
    }

    @Override
    public void update(Keyboard keyboard) {
        super.update(keyboard);

        sprite.update();

        if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A) || keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
            moving = true;

            if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A)) {
                direction = Direction.WEST;
                x -= velocityX;
            } else if (keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
                direction = Direction.EAST;
                x += velocityX;
            }

            if (x < 0) {
                x = 0;
            }

            for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
                TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
                if (isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer")) {

                    int slopeCollisionX = x + (getWidth() / 2);
                    int column = slopeCollisionX / tiledEditorMap.getTileWidth();
                    int topRow = y / tiledEditorMap.getTileHeight();
                    int bottomRow = (y + getHeight()) / tiledEditorMap.getTileHeight();

                    Tile tile = getTile(column, topRow, tiledEditorLayer);
                    Tile tileBelowPlayer = getTile(column, bottomRow, tiledEditorLayer);

                    if (isStandingInSlope(tile, tileBelowPlayer)) {
                        Direction slopeMovementDirection = getSlopeMovementDirection(tile, tileBelowPlayer);
                        if (slopeMovementDirection != null) {

                            int tileX = column * tiledEditorMap.getTileWidth();
                            int tileY = topRow * tiledEditorMap.getTileHeight();
                            if (tile == null || !tile.isSlope()) {
                                tileY = bottomRow * tiledEditorMap.getTileHeight();
                            }

                            /*
                            When moving SOUTH in a slope, the new Y-coordinate will be at the players feet.
                            This is compensated by subtracting the players height so that it will be at the players head.

                            When moving EAST in a slope, the last step in X direction is equal to the X-coordinate for the next tile.
                            This means the last step in the Y-direction is "missing" because when you step to that X-coordinate we get the next tile (next column), which is not a slope tile.
                            Since the next tile is not a slope tile, we do not calculate a new Y, and we "lose" the step in the Y-direction.
                            This is compensated by manually adding or subtracting the missing step, depending on the direction.
                             */

                            int missingSteps = 1;

                            if (slopeMovementDirection == Direction.NORTH_EAST) {
                                y = tileY - (slopeCollisionX - tileX) - missingSteps;
                            } else if (slopeMovementDirection == Direction.SOUTH_EAST) {
                                y = tileY + (slopeCollisionX - tileX) + missingSteps - getHeight();
                            } else if (slopeMovementDirection == Direction.NORTH_WEST) {
                                y = tileY - ((tileX + tiledEditorMap.getTileWidth()) - slopeCollisionX);
                            } else if (slopeMovementDirection == Direction.SOUTH_WEST) {
                                y = tileY + (tiledEditorMap.getTileHeight() - (slopeCollisionX - tileX)) - getHeight();
                            }
                        }

                    }
                }
            }
        } else {
            if (moving) {
                moving = false;
            }
        }

    }

    @Override
    public void draw(Camera camera) {
        sprite.draw(x, y, direction, camera);
    }

    private Tile getTile(int column, int row, TiledEditorLayer tiledEditorLayer) {
        int tileId = tiledEditorLayer.getData2D()[column][row];
        return tiledEditorMap.getTiles().get(tileId);
    }

    private boolean isStandingInSlope(Tile tile, Tile tileBelow) {
        return (tile != null && tile.isSlope()) || (tileBelow != null && tileBelow.isSlope());
    }

    private Direction getSlopeMovementDirection(Tile tile, Tile tileBelow) {
        if (direction == Direction.EAST && (isSlopeDirection(Direction.EAST, tile) || isSlopeDirection(Direction.EAST, tileBelow))) {
            return Direction.NORTH_EAST;
        } else if (direction == Direction.EAST && (isSlopeDirection(Direction.WEST, tile) || isSlopeDirection(Direction.WEST, tileBelow))) {
            return Direction.SOUTH_EAST;
        } else if (direction == Direction.WEST && (isSlopeDirection(Direction.EAST, tile) || isSlopeDirection(Direction.EAST, tileBelow))) {
            return Direction.SOUTH_WEST;
        } else if (direction == Direction.WEST && (isSlopeDirection(Direction.WEST, tile) || isSlopeDirection(Direction.WEST, tileBelow))) {
            return Direction.NORTH_WEST;
        } else {
            return null;
        }
    }

    private boolean isSlopeDirection(Direction direction, Tile tile) {
        return tile != null && tile.isSlope() && tile.getDirection() != null && tile.getDirection() == direction;
    }

}
