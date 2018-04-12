package no.taardal.pixelcave.level;

import com.google.gson.Gson;
import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.actor.Player;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.layer.Layer;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.service.ResourceService;
import no.taardal.pixelcave.sprite.Sprite;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    private World world;
    private List<Ribbon> ribbons;
    private Player player;

    public Level(ResourceService resourceService, Gson gson) {
        world = gson.fromJson(resourceService.readFile("worlds/world_pixelcave.json"), World.class);

        ribbons = new ArrayList<>();
        String directoryPath = "ribbons/";
        List<String> fileNames = Arrays.asList(resourceService.getFileNames(directoryPath));
        Collections.sort(fileNames);
        float speed = 0.1f;
        for (String fileName : fileNames) {
            String path = directoryPath + "/" + fileName;

            BufferedImage bufferedImage = resourceService.getBufferedImage(path);

            Ribbon ribbon = new Ribbon(new Sprite(bufferedImage));
            ribbon.setSpeedX(speed);
            ribbons.add(ribbon);
            speed += 0.1f;
        }

        BufferedImage bufferedImage = resourceService.getBufferedImage("spritesheets/knight/spritesheet-knight-" + Knight.Theme.BLACK.toString().toLowerCase() + ".png");
        SpriteSheet spriteSheet = new SpriteSheet.Builder()
                .setBufferedImage(bufferedImage)
                .setApproximateSpriteWidth(40)
                .setApproximateSpriteHeight(40)
                .build();

        player = new Knight.Builder()
                .setSpriteSheet(spriteSheet)
                .setPosition(new Vector2f(132, 100))
                .setVelocity(new Vector2f(0, 50))
                .setDirection(Direction.LEFT)
                .build();
    }

    public void handleInput(Keyboard keyboard) {
        player.handleInput(keyboard);
    }

    public void update(float secondsSinceLastUpdate, Camera camera) {
        player.update(world, secondsSinceLastUpdate);
        camera.update(player);
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).update(camera);
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
        int bottomMostRowToDraw = (camera.getY() + camera.getHeight() + world.getTileHeight()) / world.getTileHeight();
        int leftMostColumnToDraw = (camera.getX() - world.getTileWidth()) / world.getTileWidth();
        int rightMostColumnToDraw = (camera.getX() + camera.getWidth() + world.getTileWidth()) / world.getTileWidth();
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
                drawTile(row, column, tileLayer, camera);
            }
        }
    }

    private void drawTile(int row, int column, TileLayer tileLayer, Camera camera) {
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
