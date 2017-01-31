package no.taardal.blossom.sprite;

import no.taardal.blossom.view.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {

    private BufferedImage bufferedImage;

    public Sprite(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Sprite(Color color, int width, int height) {
        this(width, height);
        setColor(color);
    }

    public Sprite(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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

    private void setColor(Color color) {
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(color);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics2D.dispose();
    }

    public void draw(int x, int y, Graphics2D graphics2D) {
        graphics2D.drawImage(bufferedImage, x, y, null);
    }
    public void draw(int x, int y, Camera camera) {
        camera.drawImage(bufferedImage, x, y);
    }
}
