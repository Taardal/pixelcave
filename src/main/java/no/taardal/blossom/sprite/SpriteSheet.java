package no.taardal.blossom.sprite;

import no.taardal.blossom.coordinate.XYCoordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet {

    public static final SpriteSheet TOP_DOWN_TILES = new SpriteSheet("sprites/tiles.png");
    public static final SpriteSheet ISO_VOID = new SpriteSheet("sprites/iso_void.png");
    public static final SpriteSheet ISO_TEST = new SpriteSheet("sprites/iso_tile.png");

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteSheet.class);
    private BufferedImage bufferedImage;
    private BufferedImage[][] bufferedImages;

    public SpriteSheet(String path) {
        this.bufferedImage = getBufferedImage(path);
    }

    public SpriteSheet(String path, int spriteWidth, int spriteHeight) {
        this(path);
        bufferedImages = getBufferedImages(spriteWidth, spriteHeight);
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    public Sprite getSprite(XYCoordinate xyCoordinate) {
        BufferedImage bufferedImage = bufferedImages[xyCoordinate.getY()][xyCoordinate.getX()];
        return new Sprite(bufferedImage);
    }

    public Sprite getSprite(XYCoordinate xyCoordinate, int spriteWidth, int spriteHeight) {
        return getSprite(xyCoordinate.getX(), xyCoordinate.getY(), spriteWidth, spriteHeight);
    }

    public Sprite getSprite(int x, int y, int spriteWidth, int spriteHeight) {
        x *= spriteWidth;
        y *= spriteHeight;
        BufferedImage subimage = bufferedImage.getSubimage(x, y, spriteWidth, spriteHeight);
        return new Sprite(subimage);
    }

    private BufferedImage getBufferedImage(String path) {
        try {
            return ImageIO.read(getResourceURL(path));
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.error("Could not load image", e);
            throw new RuntimeException(e);
        }
    }

    private URL getResourceURL(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    public BufferedImage[][] getBufferedImages(int spriteWidth, int spriteHeight) {
        int columns = bufferedImage.getWidth() / spriteWidth;
        int rows = bufferedImage.getHeight() / spriteHeight;
        BufferedImage[][] bufferedImages = new BufferedImage[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                bufferedImages[row][column] = bufferedImage.getSubimage(column * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
            }
        }
        return bufferedImages;
    }

}
