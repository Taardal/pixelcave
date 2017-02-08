package no.taardal.blossom.gamestate;

import no.taardal.blossom.game.Game;
import no.taardal.blossom.input.Key;
import no.taardal.blossom.input.Keyboard;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.level.TestLevel;
import no.taardal.blossom.listener.ExitListener;
import no.taardal.blossom.menu.Menu;
import no.taardal.blossom.view.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class MenuGameState implements GameState {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuGameState.class);

    private ExitListener exitListener;
    private List<Level> levels;
    private Menu menu;
    private Font headerFont;
    private Font footerFont;

    MenuGameState(ExitListener exitListener) {
        this.exitListener = exitListener;
        levels = new ArrayList<>();
        levels.add(new TestLevel());
        menu = getMenu();
        headerFont = new Font("Arial", Font.PLAIN, 28);
        footerFont = new Font("Arial", Font.PLAIN, 10);
    }

    @Override
    public GameState update(Keyboard keyboard) {
        if (keyboard.isPressed(Key.ENTER)) {
            if (menu.getSelectedMenuItem() == 0) {
                LOGGER.info("Entering play state on level [{}].", levels.get(0));
                return new PlayGameState(new TestLevel());
            } else if (menu.getSelectedMenuItem() == 1) {
                LOGGER.info("Exiting game.");
                exitListener.onExit();
            }
        }
        if (keyboard.isPressed(Key.UP) || keyboard.isPressed(Key.W)) {
            menu.selectPreviousMenuItem();
        }
        if (keyboard.isPressed(Key.DOWN) || keyboard.isPressed(Key.S)) {
            menu.selectNextMenuItem();
        }
        return null;
    }

    @Override
    public void draw(Camera camera) {
        camera.drawString("BLOSSOM", 100, 50, headerFont, Color.WHITE);
        camera.drawString("2017, Torbjørn Årdal", 20, Game.GAME_HEIGHT - 20, footerFont, Color.WHITE);
        menu.draw(camera);
    }

    private Menu getMenu() {
        Menu menu = new Menu();
        menu.setMenuItems(new String[]{"Start", "Exit"});
        menu.setFont(new Font("Arial", Font.PLAIN, 14));
        menu.setFontColor(Color.WHITE);
        menu.setX(180);
        menu.setY(100);
        menu.setMenuItemMargin(20);
        menu.setIndicatorDiameter(10);
        return menu;
    }

}
