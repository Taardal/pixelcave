package no.taardal.blossom.sprite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteSheet.class);

    private BufferedImage[][] subImages;
    private int spriteWidth;
    private int spriteHeight;
    private int width;
    private int height;

    public SpriteSheet(BufferedImage bufferedImage, int spriteWidth, int spriteHeight) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        subImages = getSubImages(bufferedImage);
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Sprite getSprite(int column, int row) {
        return new Sprite(subImages[column][row]);
    }

    private BufferedImage[][] getSubImages(BufferedImage bufferedImage) {
        int columns = bufferedImage.getWidth() / spriteWidth;
        int rows = bufferedImage.getHeight() / spriteHeight;
        BufferedImage[][] subImages = new BufferedImage[columns][rows];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int x = column * spriteWidth;
                int y = row * spriteHeight;
                subImages[column][row] = bufferedImage.getSubimage(x, y, spriteWidth, spriteHeight);
            }
        }
        return subImages;
    }

}
