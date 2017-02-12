package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.tile.TiledEditorTileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TiledEditorLevel implements Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorLevel.class);

    private TiledEditorMap tiledEditorMap;
    private Map<Integer, Tile> tiles;

    public TiledEditorLevel(TiledEditorMap tiledEditorMap) {
        this.tiledEditorMap = tiledEditorMap;
        tiles = getTiles(tiledEditorMap);
    }

    @Override
    public void update(Keyboard keyboard) {

    }

    @Override
    public void draw(Camera camera) {
        int tileWidthExponent = Math.getExponent(tiledEditorMap.getTileWidth());
        int tileHeightExponent = Math.getExponent(tiledEditorMap.getTileHeight());

        int topMostTileRowToDraw = ((int) camera.getMinY() - tiledEditorMap.getTileHeight()) >> tileWidthExponent;
        int leftMostTileColumnToDraw = ((int) camera.getMinX() - tiledEditorMap.getTileWidth()) >> tileHeightExponent;
        int rightMostTileColumnToDraw = ((int) camera.getMaxX() + tiledEditorMap.getTileWidth()) >> tileHeightExponent;
        int bottomMostTileRowToDraw = ((int) camera.getMaxY() + tiledEditorMap.getTileHeight()) >> tileWidthExponent;

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
                    int tiledId = getLayerData(tiledEditorLayer)[column][row];
                    if (tiledId != TiledEditorMap.NO_TILE_ID) {
                        tiles.get(tiledId).draw(x, y, camera);
                    }
                }
            }
        }

    }

    private int[][] getLayerData(TiledEditorLayer tiledEditorLayer) {
        int[][] layerData = new int[tiledEditorLayer.getWidth()][tiledEditorLayer.getHeight()];
        for (int j = 0; j < layerData.length; j++) {
            for (int k = 0; k < layerData[j].length; k++) {
                layerData[j][k] = tiledEditorLayer.getData()[j + k * layerData.length];
            }
        }
        return layerData;
    }

    private Map<Integer, Tile> getTiles(TiledEditorMap tiledEditorMap) {
        Map<Integer, Tile> tiles = new HashMap<>();
        for (TiledEditorTileSet tiledEditorTileSet : tiledEditorMap.getTiledEditorTileSets()) {
            int globalId = tiledEditorTileSet.getFirstGlobalId();
            for (Tile tile : tiledEditorTileSet.getTiles()) {
                tiles.put(globalId, tile);
                globalId++;
            }
        }
        return tiles;
    }

}
