package no.taardal.blossom.menu;

import java.awt.*;

public class MenuItem {

    private String text;
    private Font font;
    private Color fontColor;

    public MenuItem(String text, Font font, Color fontColor) {
        this.text = text;
        this.font = font;
        this.fontColor = fontColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

}
