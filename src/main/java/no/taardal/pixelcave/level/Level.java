package no.taardal.pixelcave.level;

import no.taardal.pixelcave.actor.Enemy;
import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.actor.Player;
import no.taardal.pixelcave.builder.KnightBuilder;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.gameobject.GameObject;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.layer.Layer;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.ribbon.RibbonManager;
import no.taardal.pixelcave.service.GameAssetService;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    private World world;
    private RibbonManager ribbonManager;
    private List<Enemy> enemies;
    private Player player;

    public Level(GameAssetService gameAssetService) {
        world = getWorld(gameAssetService);
        ribbonManager = getRibbonManager(gameAssetService);
        enemies = getEnemies(gameAssetService);

        player = new KnightBuilder(gameAssetService)
                .theme(Knight.Theme.BLUE)
                .position(50, 0)
                .world(world)
                .build();
    }

    public void handleInput(Keyboard keyboard) {
        player.handleInput(keyboard);
    }

    public void update(float secondsSinceLastUpdate, Camera camera) {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).nextMove(player);
        }
        player.update(secondsSinceLastUpdate);
        camera.update(player.getPosition());
        camera.update(0, 0);
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

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(camera);
        }
    }

    protected abstract World getWorld(GameAssetService gameAssetService);

    protected abstract RibbonManager getRibbonManager(GameAssetService gameAssetService);

    protected abstract Enemy getEnemy(GameObject actorGameObject, GameAssetService gameAssetService);

    private List<Enemy> getEnemies(GameAssetService gameAssetService) {
        return new ArrayList<>();
        /*
        List<Enemy> enemies = new ArrayList<>();
        GameObjectLayer actorGameObjectLayer = (GameObjectLayer) world.getLayers().get("actor_layer");
        for (int i = 0; i < actorGameObjectLayer.getGameObjects().size(); i++) {
            GameObject actorGameObject = actorGameObjectLayer.getGameObjects().get(i);
            if (actorGameObject.getType().equals("enemy")) {
                Enemy enemy = getEnemy(actorGameObject, gameAssetService);
                enemy.setWorld(world);
                enemies.add(enemy);
            }
        }
        return enemies;
        */
    }

    private void drawTiles(Camera camera) {
        for (Iterator<Layer> layerIterator = world.getLayers().values().iterator(); layerIterator.hasNext(); ) {
            Layer layer = layerIterator.next();
            if (layer.isVisible() && layer.getType() == Layer.Type.TILE_LAYER) {
                drawTiles((TileLayer) layer, camera);
            }
        }
    }

    private void drawTiles(TileLayer tileLayer, Camera camera) {
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
                int tileId = tileLayer.getTileGrid()[column][row];
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
