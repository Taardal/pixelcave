package no.taardal.blossom.thread;

import no.taardal.blossom.listener.GameLoopListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLoop implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);
    private static final int ONE_SECOND = 1000;
    private static final int ONE_NANOSECOND = 1000000000;
    private static final double UPDATES_PER_SECOND = 60;
    private static final double NANOSECONDS_PER_UPDATE = ONE_NANOSECOND / UPDATES_PER_SECOND;

    private GameLoopListener gameLoopListener;
    private boolean running;
    private int frames;
    private int updates;
    private float delta;

    public GameLoop(GameLoopListener gameLoopListener) {
        this.gameLoopListener = gameLoopListener;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
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
                gameLoopListener.onHandleInput();
                gameLoopListener.onUpdate();
                updates++;
                delta--;
            }
            gameLoopListener.onDraw();
            frames++;
            if (System.currentTimeMillis() - lastTimeMillis > ONE_SECOND) {
                lastTimeMillis += ONE_SECOND;
                LOGGER.info(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }

}
