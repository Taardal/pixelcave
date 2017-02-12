package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.orientation.Orientation;
import no.taardal.blossom.renderorder.RenderOrder;
import no.taardal.blossom.tile.TiledEditorTileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class TiledEditorMapDeserializer implements JsonDeserializer<TiledEditorMap> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorMapDeserializer.class);

    @Override
    public TiledEditorMap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            TiledEditorMap tiledEditorMap = new TiledEditorMap();
            tiledEditorMap.setTiledEditorTileSets(getTiledEditorTileSets(jsonObject, jsonDeserializationContext));
            tiledEditorMap.setTiledEditorLayers(getTiledEditorLayers(jsonObject, jsonDeserializationContext));
            tiledEditorMap.setOrientation(getOrientation(jsonObject));
            tiledEditorMap.setRenderOrder(getRenderOrder(jsonObject));
            tiledEditorMap.setWidth(jsonObject.get("width").getAsInt());
            tiledEditorMap.setHeight(jsonObject.get("height").getAsInt());
            tiledEditorMap.setNextObjectId(jsonObject.get("nextobjectid").getAsInt());
            tiledEditorMap.setTileHeight(jsonObject.get("tileheight").getAsInt());
            tiledEditorMap.setTileWidth(jsonObject.get("tilewidth").getAsInt());
            tiledEditorMap.setTileWidth(jsonObject.get("version").getAsInt());
            LOGGER.info("Deserialized tiled editor map [{}]", tiledEditorMap);
            return tiledEditorMap;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize tile map.", e);
            throw new RuntimeException(e);
        }
    }

    private RenderOrder getRenderOrder(JsonObject jsonObject) {
        String renderOrderString = jsonObject.get("renderorder").getAsString();
        return RenderOrder.valueOf(renderOrderString.toUpperCase().replaceAll("-", "_"));
    }

    private Orientation getOrientation(JsonObject jsonObject) {
        String orientationString = jsonObject.get("orientation").getAsString();
        return Orientation.valueOf(orientationString.toUpperCase());
    }

    private List<TiledEditorLayer> getTiledEditorLayers(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        JsonArray layersJsonArray = jsonObject.get("layers").getAsJsonArray();
        TiledEditorLayer[] tiledEditorLayers = jsonDeserializationContext.deserialize(layersJsonArray, TiledEditorLayer[].class);
        return Arrays.asList(tiledEditorLayers);
    }

    private List<TiledEditorTileSet> getTiledEditorTileSets(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        JsonArray tileSetsJsonArray = jsonObject.get("tilesets").getAsJsonArray();
        TiledEditorTileSet[] tiledEditorTileSets = jsonDeserializationContext.deserialize(tileSetsJsonArray, TiledEditorTileSet[].class);
        return Arrays.asList(tiledEditorTileSets);
    }

}