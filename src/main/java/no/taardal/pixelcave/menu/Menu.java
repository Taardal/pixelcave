package no.taardal.pixelcave.menu;

import no.taardal.pixelcave.camera.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu {

    private String[] menuItems;
    private BufferedImage indicator;
    private Font font;
    private Color fontColor;
    private int x;
    private int y;
    private int width;
    private int menuItemMargin;
    private int selectedMenuItem;
    private int fontSize;
    private int indicatorDiameter;

    public Menu() {
        menuItems = new String[]{};
        font = new Font("Arial", Font.PLAIN, fontSize);
        fontColor = Color.WHITE;
    }

    public String[] getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(String[] menuItems) {
        this.menuItems = menuItems;
    }

    public BufferedImage getIndicator() {
        return indicator;
    }

    public void setIndicator(BufferedImage indicator) {
        this.indicator = indicator;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMenuItemMargin() {
        return menuItemMargin;
    }

    public void setMenuItemMargin(int menuItemMargin) {
        this.menuItemMargin = menuItemMargin;
    }

    public int getSelectedMenuItem() {
        return selectedMenuItem;
    }

    public void setSelectedMenuItem(int selectedMenuItem) {
        this.selectedMenuItem = selectedMenuItem;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getIndicatorDiameter() {
        return indicatorDiameter;
    }

    public void setIndicatorDiameter(int indicatorDiameter) {
        this.indicatorDiameter = indicatorDiameter;
    }

    public void selectNextMenuItem() {
        selectedMenuItem++;
        if (selectedMenuItem > menuItems.length - 1) {
            selectedMenuItem = menuItems.length - 1;
        }
    }

    public void selectPreviousMenuItem() {
        selectedMenuItem--;
        if (selectedMenuItem < 0) {
            selectedMenuItem = 0;
        }
    }

    public void draw(Camera camera) {
        for (int i = 0; i < menuItems.length; i++) {
            int y = this.y + (i * menuItemMargin);
            camera.drawString(menuItems[i], x, y, font, fontColor);
            if (i == selectedMenuItem) {
                camera.drawCircle(x - 20, y - 10, indicatorDiameter, fontColor);
            }
            /*
            if (indicator != null && i == selectedMenuItem) {
                camera.drawImage(indicator, x - 20, y);
            }
            */
        }
    }
}
