package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.entity.Player;
import no.taardal.blossom.game.Game;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.layer.TiledEditorLayerType;
import no.taardal.blossom.manager.RibbonsManager;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.resourceloader.BufferedImageResourceLoader;
import no.taardal.blossom.service.SpriteSheetService;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TiledEditorLevel implements Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorLevel.class);

    private TiledEditorMap tiledEditorMap;
    private RibbonsManager ribbonManager;
    private Player player;

    Camera camera;
    int x1 = 0;
    int y1 = 0;

    public TiledEditorLevel(TiledEditorMap tiledEditorMap, RibbonsManager ribbonManager) {
        this.tiledEditorMap = tiledEditorMap;
        this.ribbonManager = ribbonManager;

        SpriteSheetService spriteSheetService = new SpriteSheetService(new BufferedImageResourceLoader());
        SpriteSheet scorpionSpriteSheet = spriteSheetService.get("scorpion/scorpion-black-sheet-x1.png");
        Sprite scorpionSprite = scorpionSpriteSheet.getSprite(0, 0);
        AnimatedSprite animatedSprite = new AnimatedSprite(scorpionSpriteSheet, scorpionSprite.getWidth(), scorpionSprite.getHeight(), 6);

        Sprite[][] sprites2D = scorpionSpriteSheet.getSprites2D();
        Sprite[] sprites = sprites2D[1];
        AnimatedSprite animatedSprite1 = new AnimatedSprite(sprites, scorpionSprite.getWidth(), scorpionSprite.getHeight());

        player = new Player(animatedSprite1, tiledEditorMap);
    }

    @Override
    public void update(Keyboard keyboard) {
        ribbonManager.update(keyboard);
        player.update(keyboard);
    }

    @Override
    public void draw(Camera camera) {
        this.camera = camera;

        float tween = 0.03f;

        int x1 = player.getX() - Game.GAME_WIDTH / 2;
        int y1 = player.getY() - Game.GAME_HEIGHT / 2;

        this.x1 += (x1 - this.x1) * tween;
        this.y1 += (y1 - this.y1) * tween;

        if (this.x1 < 0) {
            this.x1 = 0;
        }
        if (this.y1 < 0) {
            this.y1 = 0;
        }

        int previousOffsetX = camera.getOffsetX();
        int previousOffsetY = camera.getOffsetY();

        camera.setOffsetX(this.x1);
        camera.setOffsetY(this.y1);

        if (camera.getOffsetX() < previousOffsetX) {
            ribbonManager.moveRight();
        } else if (camera.getOffsetX() > previousOffsetX) {
            ribbonManager.moveLeft();
        }

        if (camera.getOffsetX() == 0 || !player.isMoving()) {
            ribbonManager.stopMovingHorizontally();
        }

        ribbonManager.draw(camera);
        drawTiles(camera);
        player.draw(camera);
    }

    private void drawTiles(Camera camera) {
        for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
            TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
            if (isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible()) {
                drawLayerTiles(tiledEditorLayer, camera);
            }
        }
    }

    private void drawLayerTiles(TiledEditorLayer tiledEditorLayer, Camera camera) {
        int topMostTileRowToDraw = (camera.getOffsetY() - tiledEditorMap.getTileHeight()) / tiledEditorMap.getTileHeight();
        int leftMostTileColumnToDraw = (camera.getOffsetX() - tiledEditorMap.getTileWidth()) / tiledEditorMap.getTileWidth();
        int rightMostTileColumnToDraw = (camera.getOffsetX() + (int) camera.getWidth() + tiledEditorMap.getTileWidth()) / tiledEditorMap.getTileWidth();
        int bottomMostTileRowToDraw = (camera.getOffsetY() + (int) camera.getHeight() + tiledEditorMap.getTileHeight()) / tiledEditorMap.getTileHeight();

        for (int row = topMostTileRowToDraw; row < bottomMostTileRowToDraw; row++) {
            if (row >= tiledEditorMap.getHeight()) {
                break;
            }
            if (row < 0) {
                continue;
            }
            int y = row * tiledEditorMap.getTileHeight() - (int) camera.getY();
            for (int column = leftMostTileColumnToDraw; column < rightMostTileColumnToDraw; column++) {
                if (column >= tiledEditorMap.getWidth()) {
                    break;
                }
                if (column < 0) {
                    continue;
                }
                int x = column * tiledEditorMap.getTileWidth() - (int) camera.getX();
                int tileId = tiledEditorLayer.getData2D()[column][row];
                if (tileId != TiledEditorMap.NO_TILE_ID) {
                    Tile tile = tiledEditorMap.getTiles().get(tileId);
                    if (tile != null) {
                        tile.draw(x, y, camera);
                    }
                }
            }
        }
    }

    private boolean isTileLayer(TiledEditorLayer tiledEditorLayer) {
        return tiledEditorLayer.getTiledEditorLayerType() == TiledEditorLayerType.TILELAYER;
    }

}
