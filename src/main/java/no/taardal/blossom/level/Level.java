package no.taardal.blossom.level;

import no.taardal.blossom.actor.Enemy;
import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.builder.KnightBuilder;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.gameobject.GameObject;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.GameObjectLayer;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.TileLayer;
import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.service.GameAssetService;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.world.World;
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
                .position(0, 0)
                .world(world)
                .build();
    }

    public void handleInput(Keyboard keyboard) {
        player.handleInput(keyboard);
    }

    public void update(double secondsSinceLastUpdate, Camera camera) {
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            enemy.nextMove(player);
        }
        player.update(secondsSinceLastUpdate);
        camera.update((int) player.getPosition().getX(), (int) player.getPosition().getY());
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

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            iterator.next().draw(camera);
        }
    }

    protected abstract World getWorld(GameAssetService gameAssetService);

    protected abstract RibbonManager getRibbonManager(GameAssetService gameAssetService);

    protected abstract Enemy getEnemy(GameObject actorGameObject, GameAssetService gameAssetService);

    private List<Enemy> getEnemies(GameAssetService gameAssetService) {
        List<Enemy> enemies = new ArrayList<>();
        GameObjectLayer actorGameObjectLayer = (GameObjectLayer) world.getLayers().get("actor_layer");
        for (int i = 0; i < actorGameObjectLayer.getGameObjects().size(); i++) {
            GameObject actorGameObject = actorGameObjectLayer.getGameObjects().get(i);
            if (actorGameObject.getType().equals("ENEMY")) {
                Enemy enemy = getEnemy(actorGameObject, gameAssetService);
                enemy.setWorld(world);
                enemies.add(enemy);
            }
        }
        return enemies;
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
