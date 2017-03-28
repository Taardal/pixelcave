package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.layer.TiledEditorLayerType;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.sprite.Sprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Actor extends Entity {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    protected Sprite sprite;
    protected TiledEditorMap tiledEditorMap;
    protected Rectangle boundingBox;
    protected boolean falling;

    public Actor(Sprite sprite, TiledEditorMap tiledEditorMap) {
        this.sprite = sprite;
        this.tiledEditorMap = tiledEditorMap;
        falling = true;
    }

    public Sprite getSprite() {
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

    @Override
    public void update(Keyboard keyboard) {
        if (falling) {
            for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
                TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
                if (isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer")) {
                    int column = x >> tiledEditorMap.getTileWidthExponent();
                    int row = (int) (y + getHeight() + velocityY) >> tiledEditorMap.getTileHeightExponent();
                    int tileId = tiledEditorLayer.getData2D()[column][row];
                    if (tileId != TiledEditorMap.NO_TILE_ID) {
                        falling = false;
                        y = (row - 1) << tiledEditorMap.getTileHeightExponent();
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
        sprite.draw(x, y, camera);
    }

    protected boolean isTileLayer(TiledEditorLayer tiledEditorLayer) {
        return tiledEditorLayer.getTiledEditorLayerType() == TiledEditorLayerType.TILELAYER;
    }
}
