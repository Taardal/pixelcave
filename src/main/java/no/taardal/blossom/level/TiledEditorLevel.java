package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.resourceloader.BufferedImageResourceLoader;
import no.taardal.blossom.ribbon.Ribbon;
import no.taardal.blossom.service.RibbonService;
import no.taardal.blossom.service.Service;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.tile.TiledEditorTileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TiledEditorLevel implements Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorLevel.class);

    private TiledEditorMap tiledEditorMap;
    private Map<Integer, Tile> tiles;
    private Ribbon ribbon;
    private Ribbon ribbon1;
    private Camera camera;
    private Service<Ribbon> ribbonService;
    BufferedImage caveInside;
    BufferedImage bgobjects;
    BufferedImage waterlayer;
    BufferedImage grey;
    BufferedImage objects;

    public TiledEditorLevel(TiledEditorMap tiledEditorMap) {
        this.tiledEditorMap = tiledEditorMap;
        tiles = getTiles(tiledEditorMap);
        BufferedImageResourceLoader bufferedImageResourceLoader = new BufferedImageResourceLoader();
        ribbonService = new RibbonService(bufferedImageResourceLoader);
        ribbon = ribbonService.get("stageb");
        caveInside = bufferedImageResourceLoader.loadResource("ribbons/stagebstatic/bg_06_caveinside_b.png");
        bgobjects = bufferedImageResourceLoader.loadResource("ribbons/stagebstatic/bg_07_bgobjects.png");
        waterlayer = bufferedImageResourceLoader.loadResource("ribbons/stagebstatic/bg_07_waterlayer.png");
        grey = bufferedImageResourceLoader.loadResource("ribbons/stagebstatic/bg_08_grey.png");
        objects = bufferedImageResourceLoader.loadResource("ribbons/stagebstatic/bg_09_objects.png");
    }

    @Override
    public void update(Keyboard keyboard) {
        if (camera != null) {
            ribbon.update(-camera.getX(), -camera.getY());
        }
    }

    @Override
    public void draw(Camera camera) {
        this.camera = camera;

        if (ribbon != null) {
            ribbon.draw(camera);
            camera.drawImage(caveInside, (int) ribbon.getX(), (int) ribbon.getY());
            camera.drawImage(bgobjects, (int) ribbon.getX(), (int) ribbon.getY());
            camera.drawImage(waterlayer, (int) ribbon.getX(), (int) ribbon.getY());
            camera.drawImage(grey, (int) ribbon.getX(), (int) ribbon.getY());
            camera.drawImage(objects, (int) ribbon.getX(), (int) ribbon.getY());
        }

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
//                        tiles.get(tiledId).draw(x, y, camera);
                    }
                }
            }
        }
    }

    @Override
    public void setRibbon(Ribbon ribbon) {
        this.ribbon = ribbon;
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
