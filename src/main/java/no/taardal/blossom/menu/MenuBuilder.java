package no.taardal.blossom.menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MenuBuilder {

    private String[] menuItems;
    private BufferedImage indicator;
    private Font font;
    private Color fontColor;
    private int x;
    private int y;
    private int width;
    private int menuItemMargin;
    private int selectedMenuItem;

    public MenuBuilder() {
        menuItems = new String[]{};
        font = new Font("Arial", Font.PLAIN, 12);
        fontColor = Color.WHITE;
    }

    public Menu build() {
        Menu menu = new Menu();
        menu.setMenuItems(menuItems);
        menu.setIndicator(indicator);
        menu.setFont(font);
        menu.setFontColor(fontColor);
        menu.setX(x);
        menu.setY(y);
        menu.setWidth(width);
        menu.setMenuItemMargin(menuItemMargin);
        menu.setSelectedMenuItem(selectedMenuItem);
        return menu;
    }

    public MenuBuilder withMenuItems(String[] menuItems) {
        this.menuItems = menuItems;
        return this;
    }

    public MenuBuilder withIndicator(BufferedImage indicator) {
        this.indicator = indicator;
        return this;
    }

    public MenuBuilder withFont(Font font) {
        this.font = font;
        return this;
    }

    public MenuBuilder withFontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public MenuBuilder withX(int x) {
        this.x = x;
        return this;
    }

    public MenuBuilder withY(int y) {
        this.y = y;
        return this;
    }

    public MenuBuilder withWidth(int width) {
        this.width = width;
        return this;
    }

    public MenuBuilder withMenuItemMargin(int menuItemMargin) {
        this.menuItemMargin = menuItemMargin;
        return this;
    }

    public MenuBuilder withSelectedMenuItem(int selectedMenuItem) {
        this.selectedMenuItem = selectedMenuItem;
        return this;
    }

}
