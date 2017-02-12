package no.taardal.blossom.tile;

import no.taardal.blossom.camera.Camera;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage bufferedImage;

    public Tile(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void draw(int x, int y, Camera camera) {
        camera.drawImage(bufferedImage, x, y);
    }

}
