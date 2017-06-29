package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.entity.Player;
import no.taardal.blossom.game.Game;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.LayerType;
import no.taardal.blossom.world.World;
import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.service.ResourceFileService;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    private World world;
    private RibbonManager ribbonManager;
    private Player player;

    Camera camera;
    int x1 = 0;
    int y1 = 0;

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
    
    public void update(Keyboard keyboard) {
        player.handleInput(keyboard);
        player.update(keyboard);
        ribbonManager.update(keyboard);
    }

    public void draw(Camera camera) {
        this.camera = camera;

        float tween = 0.03f;

        int x1 = player.getX() - Game.GAME_WIDTH / 2;
        int y1 = player.getY() - Game.GAME_HEIGHT / 2;

        this.x1 += (x1 - this.x1) * tween;
        this.y1 += (y1 - this.y1) * tween;

        if (this.x1 < 0) {
            this.x1 = 0;
        }
        if (this.y1 < 0) {
            this.y1 = 0;
        }

        int previousOffsetX = camera.getOffsetX();
        int previousOffsetY = camera.getOffsetY();

        camera.setOffsetX(this.x1);
        camera.setOffsetY(this.y1);

        if (camera.getOffsetX() < previousOffsetX) {
            ribbonManager.moveRight();
        } else if (camera.getOffsetX() > previousOffsetX) {
            ribbonManager.moveLeft();
        }

        if (!player.isMoving()) {
            ribbonManager.stopMovingHorizontally();
        }

        ribbonManager.draw(camera);
        drawTiles(camera);
        player.draw(camera);
    }

    private void drawTiles(Camera camera) {
        for (int i = 0; i < world.getLayers().size(); i++) {
            Layer layer = world.getLayers().get(i);
            if (isTileLayer(layer) && layer.isVisible()) {
                drawLayerTiles(layer, camera);
            }
        }
    }

    private void drawLayerTiles(Layer layer, Camera camera) {
        int topMostTileRowToDraw = (camera.getOffsetY() - world.getTileHeight()) / world.getTileHeight();
        int leftMostTileColumnToDraw = (camera.getOffsetX() - world.getTileWidth()) / world.getTileWidth();
        int rightMostTileColumnToDraw = (camera.getOffsetX() + (int) camera.getWidth() + world.getTileWidth()) / world.getTileWidth();
        int bottomMostTileRowToDraw = (camera.getOffsetY() + (int) camera.getHeight() + world.getTileHeight()) / world.getTileHeight();
        for (int row = topMostTileRowToDraw; row < bottomMostTileRowToDraw; row++) {
            if (row >= world.getHeight()) {
                break;
            }
            if (row < 0) {
                continue;
            }
            int y = row * world.getTileHeight() - (int) camera.getY();
            for (int column = leftMostTileColumnToDraw; column < rightMostTileColumnToDraw; column++) {
                if (column >= world.getWidth()) {
                    break;
                }
                if (column < 0) {
                    continue;
                }
                int x = column * world.getTileWidth() - (int) camera.getX();
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

    private boolean isTileLayer(Layer layer) {
        return layer.getLayerType() == LayerType.TILELAYER;
    }

}
