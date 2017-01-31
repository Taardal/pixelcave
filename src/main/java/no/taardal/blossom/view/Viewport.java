package no.taardal.blossom.view;

import no.taardal.blossom.coordinate.XYCoordinate;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Viewport {

    int xOffset;
    int yOffset;

    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;

    public Viewport(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        prepareForDrawing();
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

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void prepareForDrawing() {
        graphics2D = bufferedImage.createGraphics();
        clear();
    }

    public void finishDrawing() {
        graphics2D.dispose();
    }

    public void drawImage(Image image, int x, int y) {
        graphics2D.drawImage(image, x, y, null);
    }

    public void drawImageIsometric(Image image, int x, int y) {
        BufferedImage bufferedImage = (BufferedImage) image;
        int xIso = (x - y) * (bufferedImage.getWidth() / 2) - xOffset;
        int yIso = (x + y) * (bufferedImage.getHeight() / 2) - yOffset;
        drawImage(bufferedImage, xIso, yIso);
    }

    public void drawSprite(Sprite sprite, XYCoordinate xyCoordinate) {
        graphics2D.drawImage(sprite.getBufferedImage(), xyCoordinate.getX(), xyCoordinate.getY(), null);
    }

    public void drawTile(Tile tile, int x, int y) {
        graphics2D.drawImage(tile.getSprite().getBufferedImage(), x, y, null);
    }

    private void clear() {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

}
