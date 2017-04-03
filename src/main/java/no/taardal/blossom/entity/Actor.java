package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.layer.TiledEditorLayerType;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Actor extends Entity {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    protected AnimatedSprite sprite;
    protected TiledEditorMap tiledEditorMap;
    protected Rectangle boundingBox;
    protected boolean falling;
    protected boolean moving;

    public Actor(AnimatedSprite sprite, TiledEditorMap tiledEditorMap) {
        this.sprite = sprite;
        this.tiledEditorMap = tiledEditorMap;
        falling = true;
    }

    public AnimatedSprite getAnimatedSprite() {
        return sprite;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public void update(Keyboard keyboard) {
        if (falling) {
            for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
                TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
                if (isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer")) {

                    int column = x / tiledEditorMap.getTileWidth();
                    int row = (int) (y + getHeight() + velocityY) / tiledEditorMap.getTileHeight();
                    int tileId = tiledEditorLayer.getData2D()[column][row];
                    if (tileId != TiledEditorMap.NO_TILE_ID) {

                        Tile tile = tiledEditorMap.getTiles().get(tileId);
                        if (tile.isSlope()) {

                            int slopeCollisionX = x + (getWidth() / 2);
                            int tileX = column * tiledEditorMap.getTileWidth();
                            int tileY = row * tiledEditorMap.getTileHeight();

                            int slopeY;
                            if (tile.getDirection() == Direction.EAST) {
                                slopeY = (tileY + tiledEditorMap.getTileHeight()) - (slopeCollisionX - tileX);
                            } else {
                                slopeY = (tileY + tiledEditorMap.getTileHeight()) - ((tileX + tiledEditorMap.getTileWidth()) - slopeCollisionX);
                            }

                            if ((y + getHeight() + velocityY) > slopeY) {
                                falling = false;
                                y = slopeY - getHeight();
                            } else {
                                y += velocityY;
                            }

                        } else {
                            falling = false;
                            y = (row - 1) * tiledEditorMap.getTileHeight();
                        }

                    } else {
                        y += velocityY;
                    }
                }
            }

        }
        boundingBox.setLocation(x, y);
    }

    @Override
    public void draw(Camera camera) {
        sprite.getSprite().draw(x, y, camera);
    }

    protected boolean isTileLayer(TiledEditorLayer tiledEditorLayer) {
        return tiledEditorLayer.getTiledEditorLayerType() == TiledEditorLayerType.TILELAYER;
    }

    public void move() {

    }
}
