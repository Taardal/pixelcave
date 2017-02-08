package no.taardal.blossom.view;


import no.taardal.blossom.input.Key;
import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.menu.MenuItem;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {

    private int xOffset;
    private int yOffset;

    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;

    public Camera(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = bufferedImage.createGraphics();
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

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getTop() {
        return yOffset;
    }

    public int getLeft() {
        return xOffset;
    }

    public int getRight() {
        return xOffset + getWidth();
    }

    public int getBottom() {
        return yOffset + getHeight();
    }

    public void update(Keyboard keyboard) {
        if (keyboard.isPressed(Key.UP) || keyboard.isPressed(Key.W)) {
            yOffset--;
        }
        if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A)) {
            xOffset--;
        }
        if (keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
            xOffset++;
        }
        if (keyboard.isPressed(Key.DOWN) || keyboard.isPressed(Key.S)) {
            yOffset++;
        }
    }

    public void clear() {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
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

    public void drawString(String text, int x, int y, Font font, Color color) {
        graphics2D.setColor(color);
        graphics2D.setFont(font);
        graphics2D.drawString(text, x, y);
    }

    public void drawCircle(int x, int y, int diameter, Color color) {
        graphics2D.setColor(color);
        graphics2D.fillOval(x, y, diameter, diameter);
    }

    public void drawMenuItem(MenuItem menuItem, int x, int y) {
        graphics2D.setColor(menuItem.getFontColor());
        graphics2D.setFont(menuItem.getFont());
        graphics2D.drawString(menuItem.getText(), x, y);
    }

}
