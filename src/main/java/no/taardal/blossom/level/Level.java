package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Player;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.LayerType;
import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.service.ResourceFileService;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    private World world;
    private RibbonManager ribbonManager;
    private Player player;

    public Level(World world, RibbonManager ribbonManager) {
        this.world = world;
        this.ribbonManager = ribbonManager;

        BufferedImage bufferedImage = new ResourceFileService().getImage("spritesheets/scorpion/scorpion-black-sheet-x1.png");
        SpriteSheet scorpionSpriteSheet = new SpriteSheet(bufferedImage, 16, 16);
        Sprite scorpionSprite = scorpionSpriteSheet.getSprite(0, 0);
        AnimatedSprite animatedSprite = new AnimatedSprite(scorpionSpriteSheet, scorpionSprite.getWidth(), scorpionSprite.getHeight(), 6);

        Sprite[][] sprites2D = scorpionSpriteSheet.getSprites2D();
        Sprite[] sprites = sprites2D[1];
        AnimatedSprite animatedSprite1 = new AnimatedSprite(sprites, scorpionSprite.getWidth(), scorpionSprite.getHeight());

        player = new Player(animatedSprite1, world);
    }

    public void update(Keyboard keyboard, Camera camera) {
        player.handleInput(keyboard);
        player.update(keyboard);
        camera.update(player.getX(), player.getY());
        ribbonManager.update(getRibbonDirection(camera));
    }

    public void draw(Camera camera) {
        ribbonManager.draw(camera);
        drawTiles(camera);
        player.draw(camera);
    }

    private Direction getRibbonDirection(Camera camera) {
        if (camera.getDirection() == Direction.WEST) {
            return Direction.EAST;
        } else if (camera.getDirection() == Direction.EAST) {
            return Direction.WEST;
        } else {
            return Direction.NO_DIRECTION;
        }
    }

    private void drawTiles(Camera camera) {
        for (int i = 0; i < world.getLayers().size(); i++) {
            Layer layer = world.getLayers().get(i);
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
            if (row >= world.getHeight()) {
                break;
            }
            if (row < 0) {
                continue;
            }
            int y = row * world.getTileHeight();
            for (int column = leftMostColumnToDraw; column < rightMostColumnToDraw; column++) {
                if (column >= world.getWidth()) {
                    break;
                }
                if (column < 0) {
                    continue;
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
