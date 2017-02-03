package no.taardal.blossom.game;

import no.taardal.blossom.input.KeyEventType;
import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.input.KeyboardEventListener;
import no.taardal.blossom.listener.GameLoopListener;
import no.taardal.blossom.gamestate.GameStateManager;
import no.taardal.blossom.thread.GameLoop;
import no.taardal.blossom.view.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements GameLoopListener, KeyboardEventListener {

    public static final int GAME_WIDTH = 400;
    public static final int GAME_HEIGHT = GAME_WIDTH / 16 * 9;

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private static final int SCALE = 3;
    private static final int NUMBER_OF_BUFFERS = 3;

    private Thread gameThread;
    private GameLoop gameLoop;
    private GameStateManager gameStateManager;
    private Camera camera;

    public Game() {
        gameLoop = new GameLoop(this);
        gameThread = new Thread(gameLoop);
        gameStateManager = new GameStateManager();
        camera = new Camera(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        addKeyListener(new Keyboard(this));
    }

    public synchronized void start() {
        requestFocus();
        gameLoop.setRunning(true);
        gameThread.start();
    }

    public synchronized void stop() {
        gameLoop.setRunning(false);
        joinGameThread();
        System.exit(0);
    }

    @Override
    public void onKeyEvent(KeyEventType keyEventType, KeyEvent keyEvent) {
        gameStateManager.onKeyEvent(keyEventType, keyEvent);
    }

    @Override
    public void onHandleInput() {

    }

    @Override
    public void onUpdate() {
        gameStateManager.update();
    }

    @Override
    public void onDraw() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(NUMBER_OF_BUFFERS);
        } else {
            camera.clear();
            gameStateManager.draw(camera);
            drawBuffer(bufferStrategy);
            bufferStrategy.show();
        }

    }

    private synchronized void joinGameThread() {
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            LOGGER.error("Could not join thread [{}]", gameThread, e);
            throw new RuntimeException(e);
        }
    }

    private void drawBuffer(BufferStrategy bufferStrategy) {
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(camera.getBufferedImage(), 0, 0, getWidth(), getHeight(), null);
        graphics.dispose();
    }
}
