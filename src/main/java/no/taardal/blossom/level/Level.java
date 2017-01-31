package no.taardal.blossom.level;

import no.taardal.blossom.Game;
import no.taardal.blossom.view.Camera;
import no.taardal.blossom.tile.Tile;

import java.awt.*;

public class Level {

    private int[][] tiles;
    private int columns;
    private int rows;
    private int width;
    private int height;

    public Level() {
        columns = 5;
        rows = 5;
        width = rows * Tile.WIDTH;
        height = columns * Tile.HEIGHT;
        tiles = new int[rows][columns];
    }

    public void draw(Camera camera, Graphics2D graphics2D) {
        int tileWidthExponent = Math.getExponent(Tile.WIDTH);
        int tileHeightExponent = Math.getExponent(Tile.HEIGHT);

        int top = (camera.getTop() - Tile.HEIGHT) >>  Tile.HEIGHT_EXPONENT;
        int left = (camera.getLeft() - Tile.WIDTH) >> Tile.WIDTH_EXPONENT;
        int right = (camera.getRight() + Tile.WIDTH) >> Tile.WIDTH_EXPONENT;
        int bottom = (camera.getBottom() + Tile.HEIGHT) >>  Tile.HEIGHT_EXPONENT;

        for (int row = top; row < bottom; row++) {
            int y = row * Tile.HEIGHT - camera.getYOffset();
            for (int column = left; column < right; column++) {
                int x = column * Tile.WIDTH - camera.getXOffset();
                getTile(column, row).draw(x, y, graphics2D);
            }
        }
    }

    public void draw(int xOffset, int yOffset, Graphics2D graphics2D) {
        int tileWidthExponent = Math.getExponent(Tile.WIDTH);
        int tileHeightExponent = Math.getExponent(Tile.HEIGHT);

        int top = (yOffset - Tile.HEIGHT) >> tileHeightExponent;
        int left = (xOffset - Tile.WIDTH) >> tileWidthExponent;
        int right = (xOffset + Game.GAME_WIDTH + Tile.WIDTH) >> tileWidthExponent;
        int bottom = (yOffset + Game.GAME_HEIGHT + Tile.HEIGHT) >> tileHeightExponent;

        for (int row = top; row < bottom; row++) {
            int y = row * Tile.HEIGHT - yOffset;
            for (int column = left; column < right; column++) {
                int x = column * Tile.WIDTH - xOffset;
                getTile(column, row).draw(x, y, graphics2D);
            }
        }
    }

    public void draw(int xOffset, int yOffset, Camera camera) {
        int tileWidthExponent = Math.getExponent(Tile.WIDTH);
        int tileHeightExponent = Math.getExponent(Tile.HEIGHT);

        int top = (camera.getTop() - Tile.HEIGHT) >> tileHeightExponent;
        int left = (camera.getLeft() - Tile.WIDTH) >> tileWidthExponent;
        int right = (camera.getRight() + Tile.WIDTH) >> tileWidthExponent;
        int bottom = (camera.getBottom() + Tile.HEIGHT) >> tileHeightExponent;

        for (int row = top; row < bottom; row++) {
            int y = row * Tile.HEIGHT - yOffset;
            for (int column = left; column < right; column++) {
                int x = column * Tile.WIDTH - xOffset;
                getTile(column, row).draw(x, y, camera);
            }
        }
    }

    public void drawIsometric(int xOffset, int yOffset, Camera camera) {
        camera.setOffset(xOffset, yOffset);

        for (int row = 0; row < 20; row++) {
            for (int column = 0; column < 20; column++) {
                camera.drawImageIsometric(Tile.ISO_GRASS.getSprite().getBufferedImage(), column, row);
            }
        }
    }

    private Tile getTile(int x, int y) {
        if (x < 0 || x >= columns || y < 0 || y >= rows) {
            return Tile.VOID;
        }
        if (tiles[y][x] == 0) {
            return Tile.GRASS;
        }
        return Tile.VOID;
    }

    private Tile getIsoTile(int x, int y) {
        if (x < 0 || x >= columns || y < 0 || y >= rows) {
            return Tile.ISO_VOID;
        }
        if (tiles[y][x] == 0) {
            return Tile.ISO_GRASS;
        }
        return Tile.ISO_VOID;
    }
}
