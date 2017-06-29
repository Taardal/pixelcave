package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.gameobject.GameObject;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.LayerType;
import no.taardal.blossom.order.DrawOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TiledEditorLayerDeserializer implements JsonDeserializer<Layer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorLayerDeserializer.class);

    @Override
    public Layer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Layer layer = new Layer();
            layer.setName(jsonObject.get("name").getAsString());
            layer.setX(jsonObject.get("x").getAsInt());
            layer.setY(jsonObject.get("y").getAsInt());
            layer.setWidth(jsonObject.get("width").getAsInt());
            layer.setHeight(jsonObject.get("height").getAsInt());
            layer.setOpacity(jsonObject.get("opacity").getAsInt());
            layer.setVisible(jsonObject.get("visible").getAsBoolean());
            layer.setLayerType(getLayerType(jsonObject));
            if (layer.getLayerType() == LayerType.TILELAYER) {
                int[] data = getData(jsonObject);
                layer.setTileGrid(getTileGrid(data, layer));
            } else if (layer.getLayerType() == LayerType.OBJECTGROUP) {
                layer.setDrawOrder(getDrawOrder(jsonObject));
                layer.setGameObjects(getGameObjects(jsonObject));
            } else {
                LOGGER.warn("Could not determine layer type.");
            }
            LOGGER.info("Deserialized layer [{}].", layer);
            return layer;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            throw new RuntimeException(e);
        }
    }

    private LayerType getLayerType(JsonObject jsonObject) {
        String layerType = jsonObject.get("type").getAsString();
        return LayerType.valueOf(layerType.toUpperCase().replaceAll("-", "_"));
    }

    private int[][] getTileGrid(int[] data, Layer layer) {
        int[][] data2D = new int[layer.getWidth()][layer.getHeight()];
        for (int x = 0; x < data2D.length; x++) {
            for (int y = 0; y < data2D[x].length; y++) {
                data2D[x][y] = data[x + y * data2D.length];
            }
        }
        return data2D;
    }

    private int[] getData(JsonObject jsonObject) {
        JsonArray dataJsonArray = jsonObject.get("data").getAsJsonArray();
        int[] data = new int[dataJsonArray.size()];
        for (int i = 0; i < dataJsonArray.size(); i++) {
            data[i] = dataJsonArray.get(i).getAsInt();
        }
        return data;
    }

    private DrawOrder getDrawOrder(JsonObject jsonObject) {
        String layerType = jsonObject.get("draworder").getAsString();
        return DrawOrder.valueOf(layerType.toUpperCase().replaceAll("-", "_"));
    }

    private List<GameObject> getGameObjects(JsonObject jsonObject) {
        List<GameObject> gameObjects = new ArrayList<>();
        JsonArray objectsJsonArray = jsonObject.get("objects").getAsJsonArray();
        for (int i = 0; i < objectsJsonArray.size(); i++) {
            gameObjects.add(getTiledEditorObject(objectsJsonArray.get(i).getAsJsonObject()));
        }
        return gameObjects;
    }

    private GameObject getTiledEditorObject(JsonObject jsonObject) {
        GameObject gameObject = new GameObject(jsonObject.get("id").getAsInt());
        gameObject.setName(jsonObject.get("name").getAsString());
        gameObject.setType(jsonObject.get("type").getAsString());
        gameObject.setWidth(jsonObject.get("width").getAsInt());
        gameObject.setHeight(jsonObject.get("height").getAsInt());
        gameObject.setX(jsonObject.get("x").getAsFloat());
        gameObject.setY(jsonObject.get("y").getAsFloat());
        gameObject.setRotation(jsonObject.get("rotation").getAsFloat());
        gameObject.setVisible(jsonObject.get("visible").getAsBoolean());
        return gameObject;
    }

}
