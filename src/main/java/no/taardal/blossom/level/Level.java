package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.LayerType;
import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    private World world;
    private RibbonManager ribbonManager;
    private Player player;

    public Level(World world, RibbonManager ribbonManager) {
        this.world = world;
        this.ribbonManager = ribbonManager;
        player = new Player(world);
    }

    public void update(double secondsSinceLastUpdate, Keyboard keyboard, Camera camera) {
        player.handleInput(keyboard);
        player.update(secondsSinceLastUpdate);
        camera.update(player.getX(), player.getY());
        ribbonManager.update(camera);
    }

    public void draw(Camera camera) {
        ribbonManager.draw(camera);
        drawTiles(camera);
        player.draw(camera);
    }

    private void drawTiles(Camera camera) {
        for (Iterator<Layer> layerIterator = world.getLayers().values().iterator(); layerIterator.hasNext();) {
            Layer layer = layerIterator.next();
            if (layer.isVisible() && layer.getLayerType() == LayerType.TILELAYER) {
                drawTiles(layer, camera);
            }
        }
    }

    private void drawTiles(Layer layer, Camera camera) {
        int topMostRowToDraw = (camera.getOffsetY() - world.getTileHeight()) / world.getTileHeight();
        int leftMostColumnToDraw = (camera.getOffsetX() - world.getTileWidth()) / world.getTileWidth();
        int rightMostColumnToDraw = (camera.getOffsetX() + (int) camera.getWidth() + world.getTileWidth()) / world.getTileWidth();
        int bottomMostRowToDraw = (camera.getOffsetY() + (int) camera.getHeight() + world.getTileHeight()) / world.getTileHeight();
        for (int row = topMostRowToDraw; row < bottomMostRowToDraw; row++) {
            if (row < 0) {
                continue;
            }
            if (row >= world.getHeight()) {
                break;
            }
            int y = row * world.getTileHeight();
            for (int column = leftMostColumnToDraw; column < rightMostColumnToDraw; column++) {
                if (column < 0) {
                    continue;
                }
                if (column >= world.getWidth()) {
                    break;
                }
                int x = column * world.getTileWidth();
                int tileId = layer.getTileGrid()[column][row];
                if (tileId != World.NO_TILE_ID) {
                    Tile tile = world.getTiles().get(tileId);
                    if (tile != null) {
                        tile.draw(x, y, camera);
                    }
                }
            }
        }
    }

}
