package no.taardal.blossom.input;

import java.awt.event.KeyEvent;

public enum Key {

    ENTER(KeyEvent.VK_ENTER),
    ESCAPE(KeyEvent.VK_ESCAPE),
    UP(KeyEvent.VK_UP),
    LEFT(KeyEvent.VK_LEFT),
    DOWN(KeyEvent.VK_DOWN),
    RIGHT(KeyEvent.VK_RIGHT),
    W(KeyEvent.VK_W),
    A(KeyEvent.VK_A),
    S(KeyEvent.VK_S),
    D(KeyEvent.VK_D);

    private int keyCode;

    Key(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

}
