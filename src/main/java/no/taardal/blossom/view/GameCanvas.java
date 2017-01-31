package no.taardal.blossom.view;

import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.state.GameStateManager;
import no.taardal.blossom.state.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas {

    private static final int NUMBER_OF_BUFFERS = 3;

    public GameCanvas(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    public void draw(Camera camera) {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(NUMBER_OF_BUFFERS);
        } else {
            Graphics graphics = bufferStrategy.getDrawGraphics();
            graphics.drawImage(camera.getBufferedImage(), 0, 0, getWidth(), getHeight(), null);
            graphics.dispose();
            bufferStrategy.show();
        }
    }

}
