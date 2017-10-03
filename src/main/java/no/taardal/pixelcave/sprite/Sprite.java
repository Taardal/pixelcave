package no.taardal.pixelcave.sprite;

import no.taardal.pixelcave.camera.Camera;

import java.awt.image.BufferedImage;

public class Sprite {

    private BufferedImage bufferedImage;

    public Sprite(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
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
        camera.drawImageFlippedHorizontally(bufferedImage, x, y);
    }

}
