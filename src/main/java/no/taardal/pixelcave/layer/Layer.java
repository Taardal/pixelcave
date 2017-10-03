package no.taardal.pixelcave.layer;

public abstract class Layer {

    public enum Type {
        GAME_OBJECT_LAYER,
        TILE_LAYER
    }

    private String name;
    private Type type;
    private int x;
    private int y;
    private int opacity;
    private boolean visible;

    public Layer(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Layer{" +
                "fileName='" + name + '\'' +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", opacity=" + opacity +
                ", visible=" + visible +
                '}';
    }
}
