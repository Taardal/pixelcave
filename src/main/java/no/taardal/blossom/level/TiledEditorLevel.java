package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.tile.TiledEditorTileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
        for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
            TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
            int[] data = tiledEditorLayer.getData();

            int rows = tiledEditorMap.getHeight();
            int columns = tiledEditorMap.getWidth();
            int[][] data2D = new int[rows][columns];

            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    int i1 = row + column * columns;
                    if (i1 < data.length) {
                        int tileId = data[i1];
                        if (tileId != TiledEditorMap.NO_TILE_ID) {
                            int x = tiledEditorLayer.getX() + column * 128;
                            int y = tiledEditorLayer.getY() + row * 128;
                            Tile tile = tiles.get(tileId);
                            tile.draw(x, y, camera);
                        }
                    }
                }
            }
        }
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
