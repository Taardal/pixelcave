package no.taardal.blossom.sprite;

import no.taardal.blossom.animation.Animation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteSheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteSheet.class);

    private Sprite[][] sprites;
    private Map<Animation.Type, Animation> animations;
    private int width;
    private int height;
    private int spriteWidth;
    private int spriteHeight;

    public SpriteSheet(BufferedImage bufferedImage, int spriteWidth, int spriteHeight) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        sprites = getSpriteImages(bufferedImage);
        animations = createAnimations();
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    public Sprite[][] getSprites() {
        return sprites;
    }

    public Map<Animation.Type, Animation> getAnimations() {
        return animations;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    protected Map<Animation.Type, Animation> createAnimations() {
        return new HashMap<>();
    }

    private Sprite[][] getSpriteImages(BufferedImage bufferedImage) {
        int columns = bufferedImage.getWidth() / spriteWidth;
        int rows = bufferedImage.getHeight() / spriteHeight;
        Sprite[][] sprites = new Sprite[columns][rows];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int x = column * spriteWidth;
                int y = row * spriteHeight;
                BufferedImage subImage = bufferedImage.getSubimage(x, y, spriteWidth, spriteHeight);
                sprites[column][row] = new Sprite(subImage);
            }
        }
        return sprites;
    }

}
