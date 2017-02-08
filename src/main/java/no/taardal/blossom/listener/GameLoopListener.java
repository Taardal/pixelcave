package no.taardal.blossom.listener;

public interface GameLoopListener {

    void onShutdown();

    void onUpdate();

    void onDraw();

}
