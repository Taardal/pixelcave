package no.taardal.blossom.tile;

import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.view.Camera;

import java.awt.*;

public class Tile {

    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    public static final int WIDTH_EXPONENT = Math.getExponent(WIDTH);
    public static final int HEIGHT_EXPONENT = Math.getExponent(HEIGHT);
    public static final Tile VOID = new Tile(SpriteSheet.TOP_DOWN_TILES.getSprite(2, 2, WIDTH, HEIGHT));
    public static final Tile GRASS = new Tile(SpriteSheet.TOP_DOWN_TILES.getSprite(0, 0, WIDTH, HEIGHT));
    public static final Tile ISO_VOID = new Tile(SpriteSheet.ISO_VOID.getSprite(0, 0, 32, 16));
    public static final Tile ISO_GRASS = new Tile(SpriteSheet.ISO_TEST.getSprite(0, 0, 32, 16));

    private Sprite sprite;

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public Tile(Color color) {
        this.sprite = new Sprite(color, WIDTH, HEIGHT);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void draw(int x, int y, Graphics2D graphics2D) {
        sprite.draw(x, y, graphics2D);
    }

    public void draw(int x, int y, Camera camera) {
        sprite.draw(x, y, camera);
    }

}
