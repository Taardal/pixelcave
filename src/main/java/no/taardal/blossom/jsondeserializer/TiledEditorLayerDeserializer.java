package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.gameobject.GameObject;
import no.taardal.blossom.layer.GameObjectLayer;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.TileLayer;
import no.taardal.blossom.order.DrawOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.*;

public class TiledEditorLayerDeserializer implements JsonDeserializer<Layer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorLayerDeserializer.class);

    @Override
    public Layer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Layer layer = getLayer(jsonObject);
            layer.setName(jsonObject.get("name").getAsString());
            layer.setX(jsonObject.get("x").getAsInt());
            layer.setY(jsonObject.get("y").getAsInt());
            layer.setOpacity(jsonObject.get("opacity").getAsInt());
            layer.setVisible(jsonObject.get("visible").getAsBoolean());
            LOGGER.info("Deserialized layer [{}].", layer);
            return layer;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            throw new RuntimeException(e);
        }
    }

    private Layer getLayer(JsonObject jsonObject) {
        String layerType = jsonObject.get("type").getAsString();
        if (layerType.equals("objectgroup")) {
            return getGameObjectLayer(jsonObject);
        } else {
            return getTileLayer(jsonObject);
        }
    }

    private GameObjectLayer getGameObjectLayer(JsonObject jsonObject) {
        GameObjectLayer gameObjectLayer = new GameObjectLayer();
        gameObjectLayer.setGameObjects(getGameObjects(jsonObject));
        gameObjectLayer.setDrawOrder(getDrawOrder(jsonObject));
        return gameObjectLayer;
    }

    private List<GameObject> getGameObjects(JsonObject jsonObject) {
        List<GameObject> gameObjects = new ArrayList<>();
        JsonArray gameObjectsJsonArray = jsonObject.get("objects").getAsJsonArray();
        for (int i = 0; i < gameObjectsJsonArray.size(); i++) {
            JsonObject gameObjectJsonObject = gameObjectsJsonArray.get(i).getAsJsonObject();
            gameObjects.add(getGameObject(gameObjectJsonObject));
        }
        return gameObjects;
    }

    private GameObject getGameObject(JsonObject jsonObject) {
        GameObject gameObject = new GameObject();
        gameObject.setProperties(getGameObjectProperties(jsonObject));
        gameObject.setId(jsonObject.get("id").getAsInt());
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

    private Map<String, Object> getGameObjectProperties(JsonObject jsonObject) {
        Map<String, Object> properties = new HashMap<>();
        JsonObject propertiesJsonObject = jsonObject.get("properties").getAsJsonObject();
        JsonObject propertyTypesJsonObject = jsonObject.get("propertytypes").getAsJsonObject();
        for (Map.Entry<String, JsonElement> propertyTypeEntry : propertyTypesJsonObject.entrySet()) {
            String propertyKey = propertyTypeEntry.getKey();
            String propertyType = propertyTypeEntry.getValue().getAsString();
            JsonElement propertyValueJsonElement = propertiesJsonObject.get(propertyKey);
            Object gameObjectProperty = getGameObjectProperty(propertyValueJsonElement, propertyType);
            properties.put(propertyKey, gameObjectProperty);
        }
        return properties;
    }

    private Object getGameObjectProperty(JsonElement propertyValueJsonElement, String propertyType) {
        if (propertyType.equals("string")) {
            return propertyValueJsonElement.getAsString();
        } else if (propertyType.equals("int")) {
            return propertyValueJsonElement.getAsInt();
        } else if (propertyType.equals("float")) {
           return propertyValueJsonElement.getAsFloat();
        } else if (propertyType.equals("bool")) {
            return propertyValueJsonElement.getAsBoolean();
        } else {
            return null;
        }
    }

    private DrawOrder getDrawOrder(JsonObject jsonObject) {
        String layerType = jsonObject.get("draworder").getAsString();
        return DrawOrder.valueOf(layerType.toUpperCase().replaceAll("-", "_"));
    }

    private TileLayer getTileLayer(JsonObject jsonObject) {
        TileLayer tileLayer = new TileLayer();
        tileLayer.setWidth(jsonObject.get("width").getAsInt());
        tileLayer.setHeight(jsonObject.get("height").getAsInt());
        tileLayer.setTileGrid(getTileGrid(jsonObject, tileLayer.getWidth(), tileLayer.getHeight()));
        return tileLayer;
    }

    private int[][] getTileGrid(JsonObject jsonObject, int width, int height) {
        int[] tiles = getTiles(jsonObject);
        int[][] tileGrid = new int[width][height];
        for (int x = 0; x < tileGrid.length; x++) {
            for (int y = 0; y < tileGrid[x].length; y++) {
                tileGrid[x][y] = tiles[x + y * tileGrid.length];
            }
        }
        return tileGrid;
    }

    private int[] getTiles(JsonObject jsonObject) {
        JsonArray dataJsonArray = jsonObject.get("data").getAsJsonArray();
        int[] data = new int[dataJsonArray.size()];
        for (int i = 0; i < dataJsonArray.size(); i++) {
            data[i] = dataJsonArray.get(i).getAsInt();
        }
        return data;
    }

}
