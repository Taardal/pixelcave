package no.taardal.blossom.layer;

import no.taardal.blossom.gameobject.TiledEditorObject;
import no.taardal.blossom.order.DrawOrder;

import java.util.Arrays;
import java.util.List;

public class TiledEditorLayer {

    private String name;
    private TiledEditorLayerType tiledEditorLayerType;
    private List<TiledEditorObject> tiledEditorObjects;
    private DrawOrder drawOrder;
    private int[][] data2D;
    private int[] data;
    private int x;
    private int y;
    private int width;
    private int height;
    private int opacity;
    private boolean visible;

    @Override
    public String toString() {
        return "TiledEditorLayer{" +
                "name='" + name + '\'' +
                ", tiledEditorLayerType=" + tiledEditorLayerType +
                ", tiledEditorObjects=" + tiledEditorObjects +
                ", drawOrder=" + drawOrder +
                ", data2D=" + Arrays.toString(data2D) +
                ", data=" + Arrays.toString(data) +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", opacity=" + opacity +
                ", visible=" + visible +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TiledEditorLayerType getTiledEditorLayerType() {
        return tiledEditorLayerType;
    }

    public void setTiledEditorLayerType(TiledEditorLayerType tiledEditorLayerType) {
        this.tiledEditorLayerType = tiledEditorLayerType;
    }

    public List<TiledEditorObject> getTiledEditorObjects() {
        return tiledEditorObjects;
    }

    public void setTiledEditorObjects(List<TiledEditorObject> tiledEditorObjects) {
        this.tiledEditorObjects = tiledEditorObjects;
    }

    public DrawOrder getDrawOrder() {
        return drawOrder;
    }

    public void setDrawOrder(DrawOrder drawOrder) {
        this.drawOrder = drawOrder;
    }

    public int[][] getData2D() {
        return data2D;
    }

    public void setData2D(int[][] data2D) {
        this.data2D = data2D;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
