package no.taardal.blossom.game;

import no.taardal.blossom.listener.GameThreadListener;
import no.taardal.blossom.state.GameStateManager;
import no.taardal.blossom.thread.GameThread;
import no.taardal.blossom.view.Camera;
import no.taardal.blossom.view.GameCanvas;

public class BlossomGame implements GameThreadListener {

    private static final int GAME_WIDTH = 400;
    private static final int GAME_HEIGHT = GAME_WIDTH / 16 * 9;
    private static final int SCALE = 3;

    private GameThread gameThread;
    private GameStateManager gameStateManager;
    private GameCanvas gameCanvas;
    private Camera camera;

    public BlossomGame() {
        gameThread = new GameThread(this);
        gameStateManager = new GameStateManager();
        gameCanvas = new GameCanvas(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE);
        camera = new Camera(GAME_WIDTH, GAME_HEIGHT);
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public synchronized void start() {
        gameThread.start();
        gameCanvas.requestFocus();
    }

    public synchronized void stop() {
        gameThread.finish();
    }

    @Override
    public void onUpdate() {
        gameStateManager.update();
    }

    @Override
    public void onDraw() {
        camera.clear();
        gameStateManager.draw(camera);
        gameCanvas.draw(camera);
    }


}
