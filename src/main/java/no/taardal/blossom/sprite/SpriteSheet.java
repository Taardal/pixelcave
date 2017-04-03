package no.taardal.blossom.sprite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteSheet.class);

    private BufferedImage[][] spriteImages;
    private Sprite[][] sprites2D;
    private Sprite[] sprites;
    private int spriteWidth;
    private int spriteHeight;
    private int width;
    private int height;

    public SpriteSheet(BufferedImage bufferedImage, int spriteWidth, int spriteHeight) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        spriteImages = getSpriteImages(bufferedImage);
        sprites2D = getSprites2D(spriteImages);
        sprites = getSprites(sprites2D);
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    private Sprite[] getSprites(Sprite[][] sprites2D) {
        Sprite[] sprites = new Sprite[sprites2D.length * sprites2D[0].length];
        int k = 0;
        for (int i = 0; i < sprites2D.length; i++) {
            for (int j = 0; j < sprites2D[i].length; j++) {
                Sprite sprite = sprites2D[i][j];
                sprites[k] = sprite;
                k++;
            }
        }
        return sprites;
    }

    private Sprite[][] getSprites2D(BufferedImage[][] spriteImages) {
        Sprite[][] sprites = new Sprite[spriteImages[0].length][spriteImages.length];
        for (int i = 0; i < spriteImages.length; i++) {
            for (int j = 0; j < spriteImages[i].length; j++) {
                BufferedImage bufferedImage = spriteImages[i][j];
                sprites[j][i] = new Sprite(bufferedImage);
            }
        }
        return sprites;
    }

    public Sprite[][] getSprites2D() {
        return sprites2D;
    }

    public Sprite[] getSprites() {
        return sprites;
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
        return new Sprite(spriteImages[column][row]);
    }

    private BufferedImage[][] getSpriteImages(BufferedImage bufferedImage) {
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
