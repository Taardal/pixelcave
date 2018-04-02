package no.taardal.pixelcave.camera;

import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.vector.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Camera {

    private static final Logger LOGGER = LoggerFactory.getLogger(Camera.class);

    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;
    private Direction direction;
    private int x;
    private int y;
    private int width;
    private int height;
    private int[] pixels;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        direction = Direction.NO_DIRECTION;

        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = bufferedImage.createGraphics();
        pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public void setX(float x) {
        this.x = (int) x;
    }

    public Direction getDirection() {
        return direction;
    }

    /*
    Tweaning = this.x += (targetX - this.x) * tweanAmount
     */
    private static final float TWEEN = 0.0275f;

    float prevPlayerX;
    public void update(float playerX, float playerY) {

        int k = (int) (width * (70 / 100.0f));
        int j = (int) (width * (30 / 100.0f));
        float v = playerX - x;
        if (v < j || v > k) {
            if (v < j) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }
            x += (int) playerX - (int) prevPlayerX;
        } else {
            direction = Direction.NO_DIRECTION;
        }
        prevPlayerX = playerX;
/*
        int playerX1 = (int) playerX;
        this.x += ((playerX1 - Game.GAME_WIDTH / 2) - x) * TWEEN;


        float v = (playerX - Game.GAME_WIDTH / 2) - x;
        float pointA = x;
        float pointB = pointA + v;

        float lerp = lerp(pointA, pointB, TWEEN);

        x = lerp;
        */
    }

    float lerp(float pointA, float pointB, float alpha) {
        return pointA + alpha * (pointB - pointA);
    }

    public void update(Vector2f playerPosition) {
        update(playerPosition.getX(), playerPosition.getY());
    }

    public void clear() {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public void drawImage(BufferedImage bufferedImage, float x, float y) {
        x -= this.x;
        y -= this.y;
        graphics2D.drawImage(bufferedImage, (int) x, (int) y, null);
    }

    public void drawImagez(BufferedImage bufferedImage, float x, float y) {
        x -= this.x;
        y -= this.y;
        LOGGER.info("DRAWING SPRITE AT [{}, {}]", x, y);
        graphics2D.drawImage(bufferedImage, (int) x, (int) y, null);
    }

    public static final int ALPHA_COLOR = 0xffff00ff;

    public void drawImagePixels(BufferedImage sprite, float positionX, float positionY) {
        positionX -= this.x;
        positionY -= this.y;

        int[] spritePixels = ((DataBufferInt) sprite.getRaster().getDataBuffer()).getData();

        for (int y = 0; y < sprite.getHeight(); y++) {
            int absoluteY = y + (int) positionY;
            for (int x = 0; x < sprite.getWidth(); x++) {
                int absoluteX = x + (int) positionX;
                if (absoluteX < 0 || absoluteX >= width || absoluteY < 0 || absoluteY >= height) {
                    continue;
                }
                int spritePixelColor = spritePixels[x + y * sprite.getWidth()];
                if (spritePixelColor != ALPHA_COLOR) {
                    pixels[absoluteX + absoluteY * width] = spritePixelColor;
                }
            }
        }

    }

    public void drawImage(BufferedImage bufferedImage, int destinationX1, int destinationX2, int destinationY1, int destinationY2, int sourceX1, int sourceX2, int sourceY1, int sourceY2) {
        graphics2D.drawImage(bufferedImage,
                destinationX1, destinationY1, destinationX2, destinationY2,
                sourceX1, sourceY1, sourceX2, sourceY2,
                null);
    }

    public void drawImageFlippedHorizontally(BufferedImage bufferedImage, float x, float y) {
        x -= this.x;
        y -= this.y;

        int sourceX1 = 0;
        int sourceX2 = bufferedImage.getWidth();
        int sourceY1 = 0;
        int sourceY2 = bufferedImage.getHeight();
        int destinationX1 = (int) x;
        int destinationX2 = (int) x + bufferedImage.getWidth();
        int destinationY1 = (int) y;
        int destinationY2 = (int) y + bufferedImage.getHeight();

        drawImage(bufferedImage,
                destinationX1, destinationX2, destinationY1, destinationY2,
                sourceX2, sourceX1, sourceY1, sourceY2);
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

    public void drawRectangle(float x, float y, int width, int height, Color color) {
        drawRectangle((int) x, (int) y, width, height, color);
    }

    public void drawRectangle(int x, int y, int width, int height, Color color) {
        x -= this.x;
        y -= this.y;
        graphics2D.setColor(color);
        graphics2D.drawRect(x, y, width, height);
    }

    public void drawRectangle(Rectangle rectangle, Color color) {
        drawRectangle(rectangle.x, rectangle.y, (int) rectangle.getWidth(), (int) rectangle.getHeight(), color);
    }

    public void drawRectangle(Rectangle2D rectangle, Color color) {
        drawRectangle((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), color);
    }

}
