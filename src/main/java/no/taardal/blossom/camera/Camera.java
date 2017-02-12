package no.taardal.blossom.camera;


import no.taardal.blossom.keyboard.Key;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.menu.MenuItem;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera extends Rectangle {

    private static final int SPEED = 10;

    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;

    public Camera(int width, int height) {
        super(width, height);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = bufferedImage.createGraphics();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void update(Keyboard keyboard) {
        if (keyboard.isPressed(Key.UP) || keyboard.isPressed(Key.W)) {
            y -= SPEED;
        }
        if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A)) {
            x -= SPEED;
        }
        if (keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
            x += SPEED;
        }
        if (keyboard.isPressed(Key.DOWN) || keyboard.isPressed(Key.S)) {
            y += SPEED;
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
        int xIso = (x - y) * (bufferedImage.getWidth() / 2) - x;
        int yIso = (x + y) * (bufferedImage.getHeight() / 2) - y;
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
