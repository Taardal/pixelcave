package no.taardal.pixelcave.spritesheet;

import no.taardal.pixelcave.animation.Animation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class SpriteSheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteSheet.class);

    BufferedImage[][] sprites;

    private Map<Animation.Type, Animation> animations;
    private BufferedImage bufferedImage;
    private int approximateSpriteWidth;
    private int approximateSpriteHeight;

    public SpriteSheet(BufferedImage bufferedImage, int approximateSpriteWidth, int approximateSpriteHeight) {
        this.bufferedImage = bufferedImage;
        this.approximateSpriteWidth = approximateSpriteWidth;
        this.approximateSpriteHeight = approximateSpriteHeight;
        sprites = createSprites();
        animations = createAnimations();
    }

    public BufferedImage[][] getSprites() {
        return sprites;
    }

    public Map<Animation.Type, Animation> getAnimations() {
        return animations;
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    abstract Map<Animation.Type, Animation> createAnimations();

    private BufferedImage[][] createSprites() {
        int columns = bufferedImage.getWidth() / approximateSpriteWidth;
        int rows = bufferedImage.getHeight() / approximateSpriteHeight;
        BufferedImage[][] sprites = new BufferedImage[columns][rows];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int approximateX = column * approximateSpriteWidth;
                int approximateY = row * approximateSpriteHeight;
                BufferedImage approximateCutout = bufferedImage.getSubimage(approximateX, approximateY, approximateSpriteWidth, approximateSpriteHeight);
                Rectangle exactCutoutBounds = getExactCutoutBounds(approximateCutout);
                if (exactCutoutBounds != null) {
                    sprites[column][row] = getSprite(approximateCutout, exactCutoutBounds);
                }
            }
        }
        return sprites;
    }

    private Rectangle getExactCutoutBounds(BufferedImage bufferedImage) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                if (!isTransparent(bufferedImage.getRGB(x, y))) {
                    minX = Math.min(x, minX);
                    minY = Math.min(y, minY);
                    maxX = Math.max(x, maxX);
                    maxY = Math.max(y, maxY);
                }
            }
        }
        if (minX != Integer.MAX_VALUE) {
            int width = maxX - minX + 1;
            int height = maxY - minY + 1;
            return new Rectangle(minX, minY, width, height);
        } else {
            return null;
        }
    }

    private boolean isTransparent(int rgb) {
        int alpha = (rgb >> 24) & 0xFF;
        return alpha == 0;
    }

    private BufferedImage getSprite(BufferedImage approximateCutout, Rectangle exactCutoutBounds) {
        BufferedImage exactCutout = new BufferedImage(exactCutoutBounds.width, exactCutoutBounds.height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < exactCutoutBounds.width; x++) {
            for (int y = 0; y < exactCutoutBounds.height; y++) {
                int rgb = approximateCutout.getRGB(x + exactCutoutBounds.x, y + exactCutoutBounds.y);
                exactCutout.setRGB(x, y, rgb);
            }
        }
        return exactCutout;
    }
}
