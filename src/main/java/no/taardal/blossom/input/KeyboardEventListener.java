package no.taardal.blossom.input;

import java.awt.event.KeyEvent;

public interface KeyboardEventListener {

    void onKeyEvent(KeyEventType keyEventType, KeyEvent keyEvent);

}
