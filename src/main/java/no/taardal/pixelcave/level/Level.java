package no.taardal.pixelcave.level;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.actor.Player;
import no.taardal.pixelcave.builder.KnightBuilder;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.layer.Layer;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.ribbon.RibbonManager;
import no.taardal.pixelcave.service.GameAssetService;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    private World world;
    private RibbonManager ribbonManager;
    private Player player;

    public Level(GameAssetService gameAssetService) {
        world = getWorld(gameAssetService);
        ribbonManager = getRibbonManager(gameAssetService);

        player = new KnightBuilder(gameAssetService)
                .world(world)
                .theme(Knight.Theme.GOLD)
                .position(new Vector2f(50, 0))
                .velocity(new Vector2f(0, 50))
                .build();
    }

    public void handleInput(Keyboard keyboard) {
        player.handleInput(keyboard);
    }

    public void update(float secondsSinceLastUpdate, Camera camera) {
        player.update(secondsSinceLastUpdate);
        camera.update(player.getPosition());
        camera.update(0, 0);
        ribbonManager.update(camera);
    }

    public void draw(Camera camera) {
        ribbonManager.draw(camera);
        drawTiles(camera);
        player.draw(camera);
    }

    protected abstract World getWorld(GameAssetService gameAssetService);

    protected abstract RibbonManager getRibbonManager(GameAssetService gameAssetService);

    private void drawTiles(Camera camera) {
        for (Layer layer : world.getLayers().values()) {
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
            for (int column = leftMostColumnToDraw; column < rightMostColumnToDraw; column++) {
                if (column < 0) {
                    continue;
                }
                if (column >= world.getWidth()) {
                    break;
                }
                int tileId = tileLayer.getTileGrid()[column][row];
                if (tileId != World.NO_TILE_ID) {
                    Tile tile = world.getTiles().get(tileId);
                    if (tile != null) {
                        int x = column * world.getTileWidth();
                        int y = row * world.getTileHeight();
                        tile.draw(x, y, camera);
                    }
                }
            }
        }
    }

}
