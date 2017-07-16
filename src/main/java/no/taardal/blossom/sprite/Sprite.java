package no.taardal.blossom.sprite;

import no.taardal.blossom.camera.Camera;

import java.awt.image.BufferedImage;

public class Sprite {

    private BufferedImage bufferedImage;

    public Sprite(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    public void draw(int x, int y, Camera camera) {
        camera.drawImage(bufferedImage, x, y);
    }

    public void drawFlippedHorizontally(int x, int y, Camera camera) {
        int destinationX1 = x;
        int destinationX2 = x + getWidth();
        int destinationY1 = y;
        int destinationY2 = y + getHeight();
        int sourceX1 = 0;
        int sourceX2 = bufferedImage.getWidth();
        int sourceY1 = 0;
        int sourceY2 = bufferedImage.getHeight();
        camera.drawImageFlippedHorizontally(bufferedImage,
                destinationX1, destinationX2, destinationY1, destinationY2,
                sourceX1, sourceX2, sourceY1, sourceY2);
/*
        camera.draw(new DrawImageTaskBuilder(bufferedImage)
                .sourceX1(0)
                .sourceX2(bufferedImage.getWidth())
                .sourceY1(0)
                .sourceY2(bufferedImage.getHeight())
                .destinationX1(x)
                .destinationX2(x + getWidth())
                .destinationY1(y)
                .destinationY2(y + getHeight())
                .flipHorizontally(true)
                .build());
*/
    }

}
