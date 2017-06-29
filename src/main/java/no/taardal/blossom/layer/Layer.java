package no.taardal.blossom.layer;

import no.taardal.blossom.gameobject.GameObject;
import no.taardal.blossom.order.DrawOrder;

import java.util.Arrays;
import java.util.List;

public class Layer {

    private String name;
    private LayerType layerType;
    private List<GameObject> gameObjects;
    private DrawOrder drawOrder;
    private int[][] tileGrid;
    private int x;
    private int y;
    private int width;
    private int height;
    private int opacity;
    private boolean visible;

    @Override
    public String toString() {
        return "Layer{" +
                "name='" + name + '\'' +
                ", layerType=" + layerType +
                ", gameObjects=" + gameObjects +
                ", drawOrder=" + drawOrder +
                ", tileGrid=" + Arrays.toString(tileGrid) +
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

    public LayerType getLayerType() {
        return layerType;
    }

    public void setLayerType(LayerType layerType) {
        this.layerType = layerType;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public DrawOrder getDrawOrder() {
        return drawOrder;
    }

    public void setDrawOrder(DrawOrder drawOrder) {
        this.drawOrder = drawOrder;
    }

    public int[][] getTileGrid() {
        return tileGrid;
    }

    public void setTileGrid(int[][] data2D) {
        this.tileGrid = data2D;
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
