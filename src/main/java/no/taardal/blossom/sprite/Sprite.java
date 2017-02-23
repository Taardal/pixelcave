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

}
