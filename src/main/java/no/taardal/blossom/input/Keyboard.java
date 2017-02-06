package no.taardal.blossom.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Keyboard.class);
    private static final int ENTER = 0;
    private static final int ESCAPE = 1;
    private static final int UP = 2;
    private static final int RIGHT = 3;
    private static final int LEFT = 4;
    private static final int DOWN = 5;
    private static final int W = 6;
    private static final int A = 7;
    private static final int S = 8;
    private static final int D = 9;
    private static final int MAX_KEY_CODE = 100;

    private static final int[] KEYS = {ENTER, ESCAPE, UP, DOWN, RIGHT, LEFT, W, A, S, D};

    private static boolean[] keyStates = new boolean[MAX_KEY_CODE];
    private static boolean[] previousKeyStates = new boolean[MAX_KEY_CODE];

    private KeyboardEventListener keyboardEventListener;

    public Keyboard(KeyboardEventListener keyboardEventListener) {
        this.keyboardEventListener = keyboardEventListener;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() < MAX_KEY_CODE) {
            //LOGGER.debug(keyEvent.paramString());
            keyStates[keyEvent.getKeyCode()] = true;
            //keyboardEventListener.onKeyEvent(KeyEventType.PRESSED, keyEvent);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() < MAX_KEY_CODE) {
            //LOGGER.debug(keyEvent.paramString());
            keyStates[keyEvent.getKeyCode()] = false;
            //keyboardEventListener.onKeyEvent(KeyEventType.RELEASED, keyEvent);
        }
    }

    public void update() {
        setCurrentKeyStatesAsPreviousKeyStates();
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER) {
            return keyStates[keyCode] && !previousKeyStates[keyCode];
        } else {
            return keyStates[keyCode];
        }
    }

    private void setCurrentKeyStatesAsPreviousKeyStates() {
        for (int i = 0; i < MAX_KEY_CODE; i++) {
            previousKeyStates[i] = keyStates[i];
        }
    }

}
