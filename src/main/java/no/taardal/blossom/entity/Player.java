package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Key;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Player extends Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    boolean collision;
    boolean inSlope;
    Direction direction = Direction.EAST;

    public Player(Sprite sprite, TiledEditorMap tiledEditorMap) {
        super(sprite, tiledEditorMap);
        velocityX = 1;
        velocityY = 1;
        y = 130;
        x = 150;

        Rectangle boundingBox = new Rectangle();
        int boundingBoxWidth = (sprite.getWidth() / tiledEditorMap.getTileWidth()) * tiledEditorMap.getTileWidth();
        int boundingBoxHeight = (sprite.getHeight() / tiledEditorMap.getTileHeight()) * tiledEditorMap.getTileHeight();
        LOGGER.debug("Bounding box: [{}w, {}h]", boundingBoxWidth, boundingBoxHeight);
        boundingBox.setBounds((int) x, (int) y, boundingBoxWidth, boundingBoxHeight);
        setBoundingBox(boundingBox);
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera);
        if (collision) {
            camera.drawRectangle(boundingBox, Color.GREEN);
        }
    }

    @Override
    public void update(Keyboard keyboard) {
        super.update(keyboard);
        collision = false;

        if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A) || keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {

            if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A)) {
                direction = Direction.WEST;
            } else if (keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
                direction = Direction.EAST;
            }

            for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
                TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
                if (isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer")) {

                    int x = (int) this.x;
                    int y = (int) this.y;
                    int velocityX = (int) this.velocityX;
                    int velocityY = (int) this.velocityY;

                    int tileWidth = tiledEditorMap.getTileWidth();
                    int tileHeight = tiledEditorMap.getTileHeight();

                    int nextCollisionX = x + (getWidth() / 2);
                    if (direction == Direction.WEST) {
                        nextCollisionX -= velocityX;
                    } else if (direction == Direction.EAST) {
                        nextCollisionX += velocityX;
                    }

                    int nextTopCollisionY = y;
                    int nextBottomCollisionY = y + getHeight();

                    int column = nextCollisionX / tileWidth;
                    int row = nextTopCollisionY / tileHeight;
                    int rowBelow = nextBottomCollisionY / tileHeight;

                    int tileId = tiledEditorLayer.getData2D()[column][row];
                    int tileBelowId = tiledEditorLayer.getData2D()[column][rowBelow];

                    Tile tile = tiledEditorMap.getTiles().get(tileId);
                    Tile tileBelow = tiledEditorMap.getTiles().get(tileBelowId);

                    boolean nextStepInSlope = (tile != null && tile.isSlope()) || (tileBelow != null && tileBelow.isSlope());

                    int y1 = 0;
                    int y2 = 0;

                    if ((!inSlope && nextStepInSlope) || (inSlope && nextStepInSlope) || (inSlope && !nextStepInSlope)) {

                        if (!inSlope && nextStepInSlope) {
                            inSlope = true;
                        }
                        if (inSlope && nextStepInSlope) {
                            inSlope = true;
                        }
                        if (inSlope && !nextStepInSlope) {
                            inSlope = false;
                        }

                        Direction slopeMovementDirection = null;
                        if (direction == Direction.EAST &&
                                ((tile != null && tile.getDirection() == Direction.EAST) || (tileBelow != null && tileBelow.getDirection() == Direction.EAST))) {
                            slopeMovementDirection = Direction.NORTHEAST;
                        }
                        if (direction == Direction.EAST &&
                                ((tile != null && tile.getDirection() == Direction.WEST) || (tileBelow != null && tileBelow.getDirection() == Direction.WEST))) {
                            slopeMovementDirection = Direction.SOUTHEAST;
                        }
                        if (direction == Direction.WEST &&
                                ((tile != null && tile.getDirection() == Direction.EAST) || (tileBelow != null && tileBelow.getDirection() == Direction.EAST))) {
                            slopeMovementDirection = Direction.SOUTHWEST;
                        }
                        if (direction == Direction.WEST &&
                                ((tile != null && tile.getDirection() == Direction.WEST) || (tileBelow != null && tileBelow.getDirection() == Direction.WEST))) {
                            slopeMovementDirection = Direction.NORTHWEST;
                        }

                        if (slopeMovementDirection != null) {

                            int tileX = column * tileWidth;
                            int tileY = row * tileHeight;
                            if (tile == null || !tile.isSlope()) {
                                tileY = rowBelow * tileHeight;
                            }

                            if (slopeMovementDirection == Direction.NORTHEAST) {
                                y1 = tileY - (nextCollisionX - tileX);
                            }
                            if (slopeMovementDirection == Direction.NORTHWEST) {
                                y1 = tileY - -(nextCollisionX - (tileX + tileWidth));
                            }
                            if (slopeMovementDirection == Direction.SOUTHEAST) {
                                y1 = (tileY - getHeight()) + (nextCollisionX - tileX);
                            }
                            if (slopeMovementDirection == Direction.SOUTHWEST) {
                                y1 = (tileY - getHeight()) + (tileHeight - (nextCollisionX - tileX));
                            }
                        }

                    }

                    this.x = nextCollisionX - (getWidth() / 2);
                    if (y1 != 0) {
                        LOGGER.debug("Y [{}], Y1 [{}]", y, y1);
                        this.y = y1;
                    }

                }
            }
        }

    }

    private void foobar(Keyboard keyboard) {
        if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A) || keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
            for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
                TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
                if (isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer")) {

                    if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A)) {
                        int column = (int) (x + getWidth()) >> tiledEditorMap.getTileWidthExponent();
                        int row = (int) (y + getHeight() + 1) >> tiledEditorMap.getTileHeightExponent();

                        x -= velocityX;

                        int tileId = tiledEditorLayer.getData2D()[column][row];
                        LOGGER.debug("t ID [" + tileId + "]");
                        if (tileId != TiledEditorMap.NO_TILE_ID) {
                            Tile tile = tiledEditorMap.getTiles().get(tileId);
                            LOGGER.debug("TILE [" + tile + "]");
                            if (tile != null) {

                                double tileX = column << tiledEditorMap.getTileWidthExponent();
                                double tileY = row << tiledEditorMap.getTileHeightExponent();
                                double tileWidth = tile.getWidth();
                                double tileHeight = tile.getHeight();

                                tileX -= tileWidth;
                                tileY -= tileHeight;

                                Rectangle tileBounds = new Rectangle((int) tileX, (int) tileY, (int) tileWidth, (int) tileHeight);

                                LOGGER.debug("Tile [{}x, {}y, {}id]", tileX, tileY, tileId);

                                Rectangle rectangle = new Rectangle(boundingBox);
                                rectangle.y += 1;
                                if (rectangle.intersects(tileBounds) && tile.isSlope() && tile.getDirection() != null && tile.getDirection() == Direction.EAST) {
                                    LOGGER.debug("COLLISION!");
                                    collision = true;

                                    if (tile.isSlope() && tile.getDirection() != null && tile.getDirection() == Direction.EAST) {
                                        LOGGER.debug("SLOPE");
                                        double v = tileY + (tileHeight - (x - tileX));
//                                        double v = (tileY - tileHeight) + ((x + getWidth()) - (tileX + tileWidth)) * (tileWidth / tileHeight);
//                                        double v = tileY + ((x + getWidth()) - tileX) * (tileWidth / tileHeight);
                                        LOGGER.debug("V " + v);
                                        y = v;
                                    }
                                }
                            }
                        }

                    } else if (keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {

                        int column = (int) (x + getWidth()) >> tiledEditorMap.getTileWidthExponent();
                        int row = (int) (y + getHeight() - 1) >> tiledEditorMap.getTileHeightExponent();
                        x += velocityX;

                        int tileId = tiledEditorLayer.getData2D()[column][row];
                        if (tileId != TiledEditorMap.NO_TILE_ID) {
                            Tile tile = tiledEditorMap.getTiles().get(tileId);
                            if (tile != null) {

                                double tileX = column << tiledEditorMap.getTileWidthExponent();
                                double tileY = row << tiledEditorMap.getTileHeightExponent();
                                double tileWidth = tile.getWidth();
                                double tileHeight = tile.getHeight();

                                Rectangle tileBounds = new Rectangle((int) tileX, (int) tileY, (int) tileWidth, (int) tileHeight);
                                if (boundingBox.intersects(tileBounds)) {
                                    collision = true;
                                    if (tile.isSlope() && tile.getDirection() != null && tile.getDirection() == Direction.EAST) {
                                        y = tileY - ((x + getWidth()) - tileX) * (tileWidth / tileHeight);
                                    }
                                }

                            }
                        }
                    }


                }
            }
        }
    }

}
