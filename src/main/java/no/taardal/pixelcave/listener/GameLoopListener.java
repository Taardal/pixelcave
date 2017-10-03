package no.taardal.pixelcave.listener;

public interface GameLoopListener {

    void onHandleInput();

    void onUpdate(double secondsSinceLastUpdate);

    void onDraw();

}
