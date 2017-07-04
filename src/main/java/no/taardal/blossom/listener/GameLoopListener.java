package no.taardal.blossom.listener;

public interface GameLoopListener {

    void onUpdate(double secondsSinceLastUpdate);

    void onDraw();

}
