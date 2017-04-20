package no.taardal.blossom.game;

import com.google.inject.Inject;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.gameloop.GameLoop;
import no.taardal.blossom.state.gamestate.GameState;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.listener.ExitListener;
import no.taardal.blossom.listener.GameLoopListener;
import no.taardal.blossom.manager.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements GameLoopListener, ExitListener {

    public static final String GAME_TITLE = "Blossom";
    public static final int GAME_WIDTH = 320;
    public static final int GAME_HEIGHT = 240;

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    public static final int SCALE = 3;
    private static final int NUMBER_OF_BUFFERS = 3;

    private Manager<GameState> gameStateManager;
    private GameLoop gameLoop;
    private Keyboard keyboard;
    private Camera camera;

    @Inject
    public Game(Manager<GameState> gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.gameLoop = new GameLoop(this);
        this.keyboard = new Keyboard();
        camera = new Camera(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
    }

    public synchronized void start() {
        requestFocus();
        addKeyListener(keyboard);
        new Thread(gameLoop, GAME_TITLE).start();
    }

    @Override
    public synchronized void onExit() {
        LOGGER.info("Exiting game.");
        System.exit(0);
    }

    @Override
    public void onUpdate() {
        keyboard.update();
        gameStateManager.update(keyboard);
    }

    @Override
    public void onDraw() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(NUMBER_OF_BUFFERS);
        } else {
            camera.clear();
            gameStateManager.draw(camera);
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
