package no.taardal.blossom.map;

import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.orientation.Orientation;
import no.taardal.blossom.order.RenderOrder;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.tile.TiledEditorTileSet;

import java.util.List;
import java.util.Map;

public class TiledEditorMap {

    public static final int NO_TILE_ID = 0;

    private List<TiledEditorLayer> tiledEditorLayers;
    private List<TiledEditorTileSet> tiledEditorTileSets;
    private Map<Integer, Tile> tiles;
    private Orientation orientation;
    private RenderOrder renderOrder;
    private int width;
    private int height;
    private int nextObjectId;
    private int tileWidth;
    private int tileWidthExponent;
    private int tileHeight;
    private int tileHeightExponent;
    private int version;

    @Override
    public String toString() {
        return "TiledEditorMap{" +
                "tiledEditorLayers=" + tiledEditorLayers +
                ", tiledEditorTileSets=" + tiledEditorTileSets +
                ", tiles=" + tiles +
                ", orientation=" + orientation +
                ", renderOrder=" + renderOrder +
                ", width=" + width +
                ", height=" + height +
                ", nextObjectId=" + nextObjectId +
                ", tileWidth=" + tileWidth +
                ", tileWidthExponent=" + tileWidthExponent +
                ", tileHeight=" + tileHeight +
                ", tileHeightExponent=" + tileHeightExponent +
                ", version=" + version +
                '}';
    }

    public List<TiledEditorLayer> getTiledEditorLayers() {
        return tiledEditorLayers;
    }

    public void setTiledEditorLayers(List<TiledEditorLayer> tiledEditorLayers) {
        this.tiledEditorLayers = tiledEditorLayers;
    }

    public List<TiledEditorTileSet> getTiledEditorTileSets() {
        return tiledEditorTileSets;
    }

    public void setTiledEditorTileSets(List<TiledEditorTileSet> tiledEditorTileSets) {
        this.tiledEditorTileSets = tiledEditorTileSets;
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
        this.tileWidthExponent = Math.getExponent(tileWidth);
    }

    public int getTileWidthExponent() {
        return tileWidthExponent;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
        this.tileHeightExponent = Math.getExponent(tileHeight);
    }

    public int getTileHeightExponent() {
        return tileHeightExponent;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
