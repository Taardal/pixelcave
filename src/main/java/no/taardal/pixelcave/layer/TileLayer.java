package no.taardal.pixelcave.layer;

import java.util.Arrays;

public class TileLayer extends Layer {

    private int[][] tileGrid;
    private int width;
    private int height;

    public TileLayer() {
        super(Type.TILE_LAYER);
    }

    public int[][] getTileGrid() {
        return tileGrid;
    }

    public void setTileGrid(int[][] tileGrid) {
        this.tileGrid = tileGrid;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "TileLayer{" +
                "tileGrid=" + Arrays.toString(tileGrid) +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
