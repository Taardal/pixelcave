package no.taardal.blossom.keyboard;

import java.awt.event.KeyListener;

public interface Keyboard extends KeyListener {

    void update();

    boolean isPressed(Key key);

}
