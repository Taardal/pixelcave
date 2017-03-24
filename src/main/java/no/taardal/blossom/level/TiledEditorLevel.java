package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.entity.Player;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.layer.TiledEditorLayerType;
import no.taardal.blossom.manager.Manager;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.resourceloader.BufferedImageResourceLoader;
import no.taardal.blossom.ribbon.Ribbon;
import no.taardal.blossom.service.SpriteSheetService;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TiledEditorLevel implements Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorLevel.class);

    private TiledEditorMap tiledEditorMap;
    private Manager<Ribbon> ribbonManager;
    private Player player;

    public TiledEditorLevel(TiledEditorMap tiledEditorMap, Manager<Ribbon> ribbonManager) {
        this.tiledEditorMap = tiledEditorMap;
        this.ribbonManager = ribbonManager;

        SpriteSheetService spriteSheetService = new SpriteSheetService(new BufferedImageResourceLoader());
        SpriteSheet scorpionSpriteSheet = spriteSheetService.get("scorpion/scorpion-violet-sheet-x1.png");
        Sprite scorpionSprite = scorpionSpriteSheet.getSprite(0, 0);
        player = new Player(scorpionSprite, tiledEditorMap);
    }

    @Override
    public void update(Keyboard keyboard) {
        ribbonManager.update(keyboard);
        player.update(keyboard);
    }

    @Override
    public void draw(Camera camera) {
        ribbonManager.draw(camera);
        drawTiles(camera);
        player.draw(camera);
    }

    private void drawTiles(Camera camera) {
        int topMostTileRowToDraw = ((int) camera.getMinY() - tiledEditorMap.getTileHeight()) >> tiledEditorMap.getTileWidthExponent();
        int leftMostTileColumnToDraw = ((int) camera.getMinX() - tiledEditorMap.getTileWidth()) >> tiledEditorMap.getTileHeightExponent();
        int rightMostTileColumnToDraw = ((int) camera.getMaxX() + tiledEditorMap.getTileWidth()) >> tiledEditorMap.getTileHeightExponent();
        int bottomMostTileRowToDraw = ((int) camera.getMaxY() + tiledEditorMap.getTileHeight()) >> tiledEditorMap.getTileWidthExponent();

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
                for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
                    TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
                    if (isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible()) {
                        int tiledId = tiledEditorLayer.getData2D()[column][row];
                        if (tiledId != TiledEditorMap.NO_TILE_ID) {
                            tiledEditorMap.getTiles().get(tiledId).draw(x, y, camera);
                        }
                    }
                }
            }
        }
    }

    private boolean isTileLayer(TiledEditorLayer tiledEditorLayer) {
        return tiledEditorLayer.getTiledEditorLayerType() == TiledEditorLayerType.TILELAYER;
    }

}
