package no.taardal.blossom.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Keyboard.class);
    private static final int NUMBER_OF_KEYS_ON_KEYBOARD = 256;

    private static boolean[] keys = new boolean[NUMBER_OF_KEYS_ON_KEYBOARD];

    private KeyboardEventListener keyboardEventListener;

    public Keyboard(KeyboardEventListener keyboardEventListener) {
        this.keyboardEventListener = keyboardEventListener;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        LOGGER.debug(keyEvent.paramString());
        keys[keyEvent.getKeyCode()] = true;
        keyboardEventListener.onKeyEvent(KeyEventType.PRESSED, keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        LOGGER.debug(keyEvent.paramString());
        keys[keyEvent.getKeyCode()] = false;
        keyboardEventListener.onKeyEvent(KeyEventType.RELEASED, keyEvent);
    }

}
