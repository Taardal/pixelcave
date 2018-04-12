package no.taardal.pixelcave.sprite;

import java.awt.image.BufferedImage;

public class Sprite {

    private int[] pixels;
    private int width;
    private int height;

    public Sprite(BufferedImage bufferedImage) {
        pixels = getRGB(bufferedImage);
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int[] getRGB(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[] rgbArray = new int[width * height];
        int startX = 0;
        int startY = 0;
        int offset = 0;
        int scanSize = width;
        return bufferedImage.getRGB(startX, startY, width, height, rgbArray, offset, scanSize);
    }

}
