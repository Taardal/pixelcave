package no.taardal.blossom.keyboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Keyboard.class);
    private static final int MAX_KEY_CODE = 100;

    private static boolean[] keyStates = new boolean[MAX_KEY_CODE];
    private static boolean[] previousKeyStates = new boolean[MAX_KEY_CODE];

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() < MAX_KEY_CODE) {
//            LOGGER.debug(keyEvent.paramString());
            keyStates[keyEvent.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() < MAX_KEY_CODE) {
//            LOGGER.debug(keyEvent.paramString());
            keyStates[keyEvent.getKeyCode()] = false;
        }
    }

    public void update() {
        setCurrentKeyStatesAsPreviousKeyStates();
    }

    public boolean isPressed(Key key) {
        return isPressed(key.getKeyCode());
    }

    private boolean isPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER) {
            return keyStates[keyCode] && !previousKeyStates[keyCode];
        } else {
            return keyStates[keyCode];
        }
    }

    private void setCurrentKeyStatesAsPreviousKeyStates() {
        System.arraycopy(keyStates, 0, previousKeyStates, 0, MAX_KEY_CODE);
    }

}