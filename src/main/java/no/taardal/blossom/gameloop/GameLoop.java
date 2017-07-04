package no.taardal.blossom.gameloop;

import no.taardal.blossom.listener.GameLoopListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLoop implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);
    private static final int ONE_SECOND_IN_MILLISECONDS = 1000;
    private static final int ONE_SECOND_IN_NANOSECONDS = 1000000000;
    private static final double UPDATES_PER_SECOND_TARGET = 60;
    private static final double NANOSECONDS_PER_UPDATE = ONE_SECOND_IN_NANOSECONDS / UPDATES_PER_SECOND_TARGET;

    private GameLoopListener gameLoopListener;
    private boolean running;
    private int frames;
    private int updates;
    private float delta;
    private double nanosecondsSinceLastUpdate;

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
        running = true;
        long lastTimeMillis = System.currentTimeMillis();
        long lastTimeNano = System.nanoTime();
        while (running) {
            long currentTimeNano = System.nanoTime();
            long timeSinceLastPass = currentTimeNano - lastTimeNano;
            nanosecondsSinceLastUpdate += timeSinceLastPass;
            delta += timeSinceLastPass / NANOSECONDS_PER_UPDATE;
            lastTimeNano = currentTimeNano;
            if (delta >= 1) {
                gameLoopListener.onUpdate(nanosecondsSinceLastUpdate / ONE_SECOND_IN_NANOSECONDS);
                updates++;
                delta--;
                nanosecondsSinceLastUpdate = 0;
            }
            gameLoopListener.onDraw();
            frames++;
            if (System.currentTimeMillis() - lastTimeMillis > ONE_SECOND_IN_MILLISECONDS) {
                lastTimeMillis += ONE_SECOND_IN_MILLISECONDS;
                LOGGER.info(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }

}