package no.taardal.pixelcave.level;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.actor.Player;
import no.taardal.pixelcave.builder.KnightBuilder;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.game.Game;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.layer.Layer;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.service.GameAssetService;
import no.taardal.pixelcave.service.LocalResourceService;
import no.taardal.pixelcave.spritesheet.KnightSpriteSheet;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);
    private static final int HORIZONTAL_SPEED = 5;
    private static final int VERTICAL_SPEED = 1;
    private static final double BASE_SPEED_FACTOR = 0.2;
    private static final double SPEED_FACTOR_INCREMENT = 0.1;

    private World world;
    private Player player;
    private List<Ribbon> ribbons;

    boolean foo = false;

    public Level(GameAssetService gameAssetService) {
        world = gameAssetService.getWorld("world_pixelcave.json");

        ribbons = gameAssetService.getRibbons("");
        int foo = 1;
        for (int i = 0; i < ribbons.size(); i++) {
            Ribbon ribbon = ribbons.get(i);
            ribbon.setSpeedX(foo);
            if (i % 4 == 0) {
                foo++;
            }
        }

        /*
        double speedFactor = BASE_SPEED_FACTOR;
        for (Ribbon ribbon : ribbons) {
            ribbon.setSpeedX((int) (speedFactor * HORIZONTAL_SPEED));
            ribbon.setSpeedY(VERTICAL_SPEED);
            speedFactor += SPEED_FACTOR_INCREMENT;
        }
        */

        String knightSpriteSheetPath = "spritesheets/knight/spritesheet-knight-" + Knight.Theme.BLACK.toString().toLowerCase() + ".png";
        player = new KnightBuilder()
                .setWorld(world)
                .setSpriteSheet(new KnightSpriteSheet(new LocalResourceService().getImage(knightSpriteSheetPath)))
                .setPosition(new Vector2f(132, 100))
                .setVelocity(new Vector2f(0, 50))
                .setDirection(Direction.LEFT)
                .build();
    }

    public void handleInput(Keyboard keyboard) {
        player.handleInput(keyboard);
    }

    public void update(float secondsSinceLastUpdate, Camera camera) {
        if (!foo) {
            camera.setX(player.getPosition().getX() - Game.GAME_WIDTH / 2);
            foo = true;
        }
        player.update(world, secondsSinceLastUpdate);
        camera.update(player.getPosition());
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).update(camera.getDirection());
        }
    }

    public void draw(Camera camera) {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).draw(camera);
        }
        drawTiles(camera);
        player.draw(camera);
    }

    private void drawTiles(Camera camera) {
        for (Layer layer : world.getLayers().values()) {
            if (layer.isVisible() && layer.getType() == Layer.Type.TILE_LAYER) {
                drawTiles((TileLayer) layer, camera);
            }
        }
    }

    private void drawTiles(TileLayer tileLayer, Camera camera) {
        int topMostRowToDraw = (camera.getY() - world.getTileHeight()) / world.getTileHeight();
        int leftMostColumnToDraw = (camera.getX() - world.getTileWidth()) / world.getTileWidth();
        int rightMostColumnToDraw = (camera.getX() + camera.getWidth() + world.getTileWidth()) / world.getTileWidth();
        int bottomMostRowToDraw = (camera.getY() + camera.getHeight() + world.getTileHeight()) / world.getTileHeight();
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
