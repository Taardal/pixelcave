package no.taardal.blossom.tile;

import java.util.List;

public class TileSet {

    private List<Tile> tiles;
    private String name;
    private String imagePath;
    private int firstGlobalId;
    private int numberOfColumns;
    private int numberOfTiles;
    private int tileWidth;
    private int imageWidth;
    private int imageHeight;
    private int tileHeight;
    private int margin;
    private int spacing;

    @Override
    public String toString() {
        return "TileSet{" +
                "tiles=" + tiles +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", firstGlobalId=" + firstGlobalId +
                ", numberOfColumns=" + numberOfColumns +
                ", numberOfTiles=" + numberOfTiles +
                ", tileWidth=" + tileWidth +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                ", tileHeight=" + tileHeight +
                ", margin=" + margin +
                ", spacing=" + spacing +
                '}';
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getFirstGlobalId() {
        return firstGlobalId;
    }

    public void setFirstGlobalId(int firstGlobalId) {
        this.firstGlobalId = firstGlobalId;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfTiles() {
        return numberOfTiles;
    }

    public void setNumberOfTiles(int numberOfTiles) {
        this.numberOfTiles = numberOfTiles;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }
}
