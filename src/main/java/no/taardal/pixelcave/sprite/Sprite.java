package no.taardal.pixelcave.sprite;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class Sprite {

    private int[] pixels;
    private int width;
    private int height;

    public Sprite(int[] pixels, int approximateWidth, int approximateHeight) {
        this.pixels = pixels;
        setSize(approximateWidth, approximateHeight);
    }

    public Sprite(BufferedImage bufferedImage) {
        pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
        setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
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

    private void setSize(int approximateWidth, int approximateHeight) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int x = 0; x < approximateWidth; x++) {
            for (int y = 0; y < approximateHeight; y++) {
                int pixel = pixels[x + y * approximateWidth];
                if (!isTransparent(pixel)) {
                    minX = Math.min(x, minX);
                    minY = Math.min(y, minY);
                    maxX = Math.max(x, maxX);
                    maxY = Math.max(y, maxY);
                }
            }
        }
        if (minX != Integer.MAX_VALUE) {
            width = maxX - minX + 1;
            height = maxY - minY + 1;
        }
    }

    private boolean isTransparent(int rgb) {
        int alpha = (rgb >> 24) & 0xFF;
        return alpha == 0;
    }

    public class FastRGB
    {

        private int width;
        private int height;
        private boolean hasAlphaChannel;
        private int pixelLength;
        private byte[] pixels;

        FastRGB(BufferedImage image)
        {

            pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            width = image.getWidth();
            height = image.getHeight();
            hasAlphaChannel = image.getAlphaRaster() != null;
            pixelLength = 3;
            if (hasAlphaChannel)
            {
                pixelLength = 4;
            }

        }

        int getRGB(int x, int y)
        {
            int pos = (y * pixelLength * width) + (x * pixelLength);

            int argb = -16777216; // 255 alpha
            if (hasAlphaChannel)
            {
                argb = (((int) pixels[pos++] & 0xff) << 24); // alpha
            }

            argb += ((int) pixels[pos++] & 0xff); // blue
            argb += (((int) pixels[pos++] & 0xff) << 8); // green
            argb += (((int) pixels[pos++] & 0xff) << 16); // red
            return argb;
        }
    }

}
