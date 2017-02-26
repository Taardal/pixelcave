package no.taardal.blossom.tile;

import no.taardal.blossom.camera.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage bufferedImage;
    private Rectangle boundingBox;

    public Tile(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        boundingBox = new Rectangle(bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void draw(int x, int y, Camera camera) {
        camera.drawImage(bufferedImage, x, y);
    }

}
