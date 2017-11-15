package no.taardal.pixelcave.listener;

public interface GameLoopListener {

    void onHandleInput();

    void onUpdate(float secondsSinceLastUpdate);

    void onDraw();

}
