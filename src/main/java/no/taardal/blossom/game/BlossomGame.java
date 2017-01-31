package no.taardal.blossom.game;

import no.taardal.blossom.listener.GameThreadListener;
import no.taardal.blossom.thread.GameThread;
import no.taardal.blossom.view.GameCanvas;

public class BlossomGame implements GameThreadListener {

    private GameCanvas gameCanvas;
    private GameThread gameThread;

    public BlossomGame() {
        gameCanvas = new GameCanvas();
        gameThread = new GameThread(this);
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

    }

    @Override
    public void onDraw() {

    }


}
