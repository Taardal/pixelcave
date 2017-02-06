package no.taardal.blossom.gamestate;

import no.taardal.blossom.game.Game;
import no.taardal.blossom.input.KeyEventType;
import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.level.TestLevel;
import no.taardal.blossom.listener.ExitListener;
import no.taardal.blossom.view.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

class MenuGameState implements GameState {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuGameState.class);
    private static final String[] MENU_ITEMS = {"Start", "Exit"};
    private static final int START = 0;
    private static final int EXIT = 1;

    private ExitListener exitListener;
    private List<Level> levels;
    private int selectedMenuItem;

    private Color fontColor;
    private Font titleFont;
    private Font menuFont;
    private Font signatureFont;

    private int menuIndicatorDiameter = 10;
    private int titleFontSize = 28;
    private int menuFontSize = 14;
    private int signatureFontSize = 10;
    private int titleX = 100;
    private int titleY = 50;
    private int startX = 180;
    private int startY = 100;
    private int exitX = 180;
    private int exitY = 120;
    private int signatureX = 20;
    private int signatureY = Game.GAME_HEIGHT - 20;

    MenuGameState(ExitListener exitListener) {
        this.exitListener = exitListener;
        levels = new ArrayList<>();
        levels.add(new TestLevel());

        fontColor = Color.WHITE;
        titleFont = new Font("Times New Roman", Font.PLAIN, titleFontSize);
        menuFont = new Font("Arial", Font.PLAIN, menuFontSize);
        signatureFont = new Font("Arial", Font.PLAIN, signatureFontSize);
    }

    @Override
    public GameState onKeyEvent(KeyEventType keyEventType, KeyEvent keyEvent) {
        if (keyEventType == KeyEventType.RELEASED) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                if (selectedMenuItem == START) {
                    LOGGER.debug("Entering play state on level [{}].", levels.get(0));
                    return new PlayGameState(levels.get(0));
                } else if (selectedMenuItem == EXIT) {
                    exitListener.onExit();
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                selectedMenuItem--;
                if (selectedMenuItem < 0) {
                    selectedMenuItem = 0;
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                selectedMenuItem++;
                if (selectedMenuItem > 1) {
                    selectedMenuItem = 1;
                }
            }
        }
        return null;
    }

    @Override
    public GameState update(Keyboard keyboard) {
        if (keyboard.isKeyPressed(KeyEvent.VK_UP) || keyboard.isKeyPressed(KeyEvent.VK_W)) {
            selectedMenuItem--;
            if (selectedMenuItem < 0) {
                selectedMenuItem = 0;
            }
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN) || keyboard.isKeyPressed(KeyEvent.VK_S)) {
            selectedMenuItem++;
            if (selectedMenuItem > 1) {
                selectedMenuItem = 1;
            }
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
            if (selectedMenuItem == START) {
                LOGGER.debug("Entering play state on level [{}].", levels.get(0));
                return new PlayGameState(levels.get(0));
            } else if (selectedMenuItem == EXIT) {
                exitListener.onExit();
            }
        }
        return null;
    }

    @Override
    public void draw(Camera camera) {
        camera.drawString("B L O S S O M", titleX, titleY, titleFont, fontColor);
        camera.drawString("Start", startX, startY, menuFont, fontColor);
        camera.drawString("Exit", exitX, exitY, menuFont, fontColor);
        camera.drawString("2017, Torbjørn Årdal", signatureX, signatureY, signatureFont, fontColor);
        if (selectedMenuItem == START) {
            camera.drawCircle(startX - 20, startY - 10, menuIndicatorDiameter, fontColor);
        } else if (selectedMenuItem == EXIT) {
            camera.drawCircle(exitX - 20, exitY - 10, menuIndicatorDiameter, fontColor);
        }
    }

}
