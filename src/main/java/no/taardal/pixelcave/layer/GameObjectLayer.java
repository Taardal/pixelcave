package no.taardal.pixelcave.layer;

import no.taardal.pixelcave.gameobject.GameObject;
import no.taardal.pixelcave.order.DrawOrder;

import java.util.List;

public class GameObjectLayer extends Layer {

    private List<GameObject> gameObjects;
    private DrawOrder drawOrder;

    public GameObjectLayer() {
        super(Type.GAME_OBJECT_LAYER);
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

    @Override
    public String toString() {
        return "GameObjectLayer{" +
                "gameObjects=" + gameObjects +
                ", drawOrder=" + drawOrder +
                '}';
    }
}
