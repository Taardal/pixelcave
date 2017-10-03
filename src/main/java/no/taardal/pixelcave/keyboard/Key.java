package no.taardal.pixelcave.keyboard;

import java.awt.event.KeyEvent;

public enum Key {

    ENTER(KeyEvent.VK_ENTER),
    ESCAPE(KeyEvent.VK_ESCAPE),
    SPACE(KeyEvent.VK_SPACE),
    UP(KeyEvent.VK_UP),
    LEFT(KeyEvent.VK_LEFT),
    DOWN(KeyEvent.VK_DOWN),
    RIGHT(KeyEvent.VK_RIGHT),
    W(KeyEvent.VK_W),
    A(KeyEvent.VK_A),
    S(KeyEvent.VK_S),
    D(KeyEvent.VK_D),
    Q(KeyEvent.VK_Q),
    E(KeyEvent.VK_E),
    F(KeyEvent.VK_F),
    C(KeyEvent.VK_C);

    private int keyCode;

    Key(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

}
