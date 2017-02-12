package no.taardal.blossom.gameloop;

public interface GameLoop extends Runnable {

    boolean isRunning();

    void setRunning(boolean running);

}
