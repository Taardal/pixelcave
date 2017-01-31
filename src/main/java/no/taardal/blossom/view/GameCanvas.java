package no.taardal.blossom.view;

import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.level.Level;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas {

    public static final int GAME_WIDTH = 400;
    public static final int GAME_HEIGHT = GAME_WIDTH / 16 * 9;
    public static final int SCALE = 3;
    private static final int NUMBER_OF_BUFFERS = 3;

    private Camera camera;

    public GameCanvas() {
        setPreferredSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        camera = new Camera(GAME_WIDTH, GAME_HEIGHT);
    }

    public void update(Keyboard keyboard) {
        camera.update(keyboard);
    }

    public void draw(Level level) {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(NUMBER_OF_BUFFERS);
        } else {
            camera.prepareForDrawing();
            level.draw(camera);
            camera.finishDrawing();
            drawCameraToBuffer(bufferStrategy);
            bufferStrategy.show();
        }
    }

    private void drawCameraToBuffer(BufferStrategy bufferStrategy) {
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(camera.getBufferedImage(), 0, 0, getWidth(), getHeight(), null);
        graphics.dispose();
    }

}
