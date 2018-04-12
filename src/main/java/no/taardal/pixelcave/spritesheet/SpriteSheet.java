package no.taardal.pixelcave.spritesheet;

import no.taardal.pixelcave.sprite.Sprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteSheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteSheet.class);

    private Sprite[][] sprites;
    private BufferedImage bufferedImage;
    private int approximateSpriteWidth;
    private int approximateSpriteHeight;

    private SpriteSheet(BufferedImage bufferedImage, int approximateSpriteWidth, int approximateSpriteHeight) {
        this.bufferedImage = bufferedImage;
        this.approximateSpriteWidth = approximateSpriteWidth;
        this.approximateSpriteHeight = approximateSpriteHeight;
        sprites = createSprites();
    }

    public Sprite[][] getSprites() {
        return sprites;
    }

    private Sprite[][] createSprites() {
        int rows = bufferedImage.getHeight() / approximateSpriteHeight;
        int columns = bufferedImage.getWidth() / approximateSpriteWidth;
        Sprite[][] sprites = new Sprite[columns][rows];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int approximateX = column * approximateSpriteWidth;
                int approximateY = row * approximateSpriteHeight;
                BufferedImage approximateCutoutImage = bufferedImage.getSubimage(approximateX, approximateY, approximateSpriteWidth, approximateSpriteHeight);
                Rectangle exactCutoutRectangle = getExactCutoutRectangle(approximateCutoutImage);
                if (exactCutoutRectangle != null) {
                    BufferedImage bufferedImage = getSprite(approximateCutoutImage, exactCutoutRectangle);
                    sprites[column][row] = new Sprite(bufferedImage);
                }
            }
        }
        return sprites;
    }

    private Rectangle getExactCutoutRectangle(BufferedImage bufferedImage) {
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

    private BufferedImage getSprite(BufferedImage approximateCutout, Rectangle exactCutoutRectangle) {
        BufferedImage exactCutoutImage = new BufferedImage(exactCutoutRectangle.width, exactCutoutRectangle.height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < exactCutoutRectangle.width; x++) {
            for (int y = 0; y < exactCutoutRectangle.height; y++) {
                int approximateCutoutX = x + exactCutoutRectangle.x;
                int approximateCutoutY = y + exactCutoutRectangle.y;
                int rgb = approximateCutout.getRGB(approximateCutoutX, approximateCutoutY);
                exactCutoutImage.setRGB(x, y, rgb);
            }
        }
        return exactCutoutImage;
    }

    public static class Builder {

        private BufferedImage bufferedImage;
        private int approximateSpriteWidth;
        private int approximateSpriteHeight;

        public Builder setBufferedImage(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;
            return this;
        }

        public Builder setApproximateSpriteWidth(int approximateSpriteWidth) {
            this.approximateSpriteWidth = approximateSpriteWidth;
            return this;
        }

        public Builder setApproximateSpriteHeight(int approximateSpriteHeight) {
            this.approximateSpriteHeight = approximateSpriteHeight;
            return this;
        }

        public SpriteSheet build() {
            return new SpriteSheet(bufferedImage, approximateSpriteWidth, approximateSpriteHeight);
        }

    }

}
