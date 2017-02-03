package no.taardal.blossom.listener;

public interface GameLoopListener {

    void onHandleInput();
    void onUpdate();
    void onDraw();
}
