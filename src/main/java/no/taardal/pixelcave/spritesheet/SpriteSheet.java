package no.taardal.pixelcave.spritesheet;

import no.taardal.pixelcave.sprite.Sprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class SpriteSheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteSheet.class);

    private Sprite[][] sprites;
    private BufferedImage bufferedImage;
    private int[] pixels;
    private int approximateSpriteWidth;
    private int approximateSpriteHeight;

    private SpriteSheet(BufferedImage bufferedImage, int approximateSpriteWidth, int approximateSpriteHeight) {
        this.bufferedImage = bufferedImage;
        this.approximateSpriteWidth = approximateSpriteWidth;
        this.approximateSpriteHeight = approximateSpriteHeight;
        pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
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

                BufferedImage spriteBufferedImage = bufferedImage.getSubimage(approximateX, approximateY, approximateSpriteWidth, approximateSpriteHeight);
                int width = spriteBufferedImage.getWidth();
                int height = spriteBufferedImage.getHeight();
                BufferedImage spriteImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = spriteImage.createGraphics();
                graphics.drawImage(spriteBufferedImage, 0, 0, width, height, null);
                graphics.dispose();


                sprites[column][row] = new Sprite(spriteImage);
            }
        }
        return sprites;
    }

    private int[] getSpritePixels(int row, int column) {
        int startX = column * approximateSpriteWidth;
        int startY = row * approximateSpriteHeight;
        int[] spritePixels = new int[approximateSpriteWidth * approximateSpriteHeight];
        for (int x = 0; x < approximateSpriteWidth; x++) {
            for (int y = 0; y < approximateSpriteHeight; y++) {
                int pixel = (startX + x) + (startY + y) * approximateSpriteWidth;
                spritePixels[x + y * approximateSpriteWidth] = pixels[pixel];
            }
        }
        return spritePixels;
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
