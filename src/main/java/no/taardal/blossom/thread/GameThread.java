package no.taardal.blossom.thread;

import no.taardal.blossom.listener.GameThreadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameThread.class);
    private static final int ONE_SECOND = 1000;
    private static final int ONE_NANOSECOND = 1000000000;
    private static final double UPDATES_PER_SECOND = 60;
    private static final double NANOSECONDS_PER_UPDATE = ONE_NANOSECOND / UPDATES_PER_SECOND;

    private GameThreadListener gameThreadListener;
    private boolean running;
    private int frames;
    private int updates;
    private float delta;

    public GameThread(GameThreadListener gameThreadListener) {
        this.gameThreadListener = gameThreadListener;
    }

    @Override
    public void run() {
        long lastTimeMillis = System.currentTimeMillis();
        long lastTimeNano = System.nanoTime();
        while (running) {
            long currentTimeNano = System.nanoTime();
            delta += (currentTimeNano - lastTimeNano) / NANOSECONDS_PER_UPDATE;
            lastTimeNano = currentTimeNano;
            if (delta >= 1) {
                gameThreadListener.onUpdate();
                updates++;
                delta--;
            }
            gameThreadListener.onDraw();
            frames++;
            if (System.currentTimeMillis() - lastTimeMillis > ONE_SECOND) {
                lastTimeMillis += ONE_SECOND;
                LOGGER.info(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
        joinLoop();
    }

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    public synchronized void finish() {
        running = false;
    }

    private synchronized void joinLoop() {
        try {
            super.join();
        } catch (InterruptedException e) {
            LOGGER.error("Could not join game loop", e);
            throw new RuntimeException(e);
        }
    }

}
