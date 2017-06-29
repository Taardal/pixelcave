package no.taardal.blossom.world;

import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.orientation.Orientation;
import no.taardal.blossom.order.RenderOrder;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.tile.TileSet;

import java.util.List;
import java.util.Map;

public class World {

    public static final int NO_TILE_ID = 0;

    private List<Layer> layers;
    private List<TileSet> tileSets;
    private Map<Integer, Tile> tiles;
    private Orientation orientation;
    private RenderOrder renderOrder;
    private int width;
    private int height;
    private int nextObjectId;
    private int tileWidth;
    private int tileHeight;
    private int version;

    @Override
    public String toString() {
        return "World{" +
                "layers=" + layers +
                ", tileSets=" + tileSets +
                ", tiles=" + tiles +
                ", orientation=" + orientation +
                ", renderOrder=" + renderOrder +
                ", width=" + width +
                ", height=" + height +
                ", nextObjectId=" + nextObjectId +
                ", tileWidth=" + tileWidth +
                ", tileHeight=" + tileHeight +
                ", version=" + version +
                '}';
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public List<TileSet> getTileSets() {
        return tileSets;
    }

    public void setTileSets(List<TileSet> tileSets) {
        this.tileSets = tileSets;
    }

    public Map<Integer, Tile> getTiles() {
        return tiles;
    }

    public void setTiles(Map<Integer, Tile> tiles) {
        this.tiles = tiles;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public RenderOrder getRenderOrder() {
        return renderOrder;
    }

    public void setRenderOrder(RenderOrder renderOrder) {
        this.renderOrder = renderOrder;
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

    public int getNextObjectId() {
        return nextObjectId;
    }

    public void setNextObjectId(int nextObjectId) {
        this.nextObjectId = nextObjectId;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
