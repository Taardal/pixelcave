package no.taardal.blossom.level;

import no.taardal.blossom.actor.Enemy;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.LayerType;
import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.service.ResourceService;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public abstract class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    World world;
    RibbonManager ribbonManager;
    Player player;
    List<Enemy> enemies;

    Level(String name, ResourceService resourceService) {
        world = resourceService.getWorld(name);
        ribbonManager = resourceService.getRibbonManager(name);
        enemies = getEnemies(resourceService);
        player = getPlayer(resourceService);
    }

    abstract List<Enemy> getEnemies(ResourceService resourceService);

    abstract Player getPlayer(ResourceService resourceService);

    public void handleInput(Keyboard keyboard) {
        player.handleInput(keyboard);
    }

    public void update(double secondsSinceLastUpdate, Camera camera) {
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            enemy.nextMove(player);
        }
        player.update(secondsSinceLastUpdate);
        camera.update(player.getX(), player.getY());
        ribbonManager.update(camera);
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            if (enemy.isDead()) {
                iterator.remove();
            } else {
                enemy.update(secondsSinceLastUpdate);
            }
        }
    }

    public void draw(Camera camera) {
        ribbonManager.draw(camera);
        drawTiles(camera);
        player.draw(camera);

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            iterator.next().draw(camera);
        }
    }

    private void drawTiles(Camera camera) {
        for (Iterator<Layer> layerIterator = world.getLayers().values().iterator(); layerIterator.hasNext(); ) {
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
