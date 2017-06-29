package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.tile.TileSet;
import no.taardal.blossom.world.World;
import no.taardal.blossom.orientation.Orientation;
import no.taardal.blossom.order.RenderOrder;
import no.taardal.blossom.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiledEditorWorldDeserializer implements JsonDeserializer<World> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorWorldDeserializer.class);

    @Override
    public World deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            World world = new World();
            world.setWidth(jsonObject.get("width").getAsInt());
            world.setHeight(jsonObject.get("height").getAsInt());
            world.setNextObjectId(jsonObject.get("nextobjectid").getAsInt());
            world.setTileHeight(jsonObject.get("tileheight").getAsInt());
            world.setTileWidth(jsonObject.get("tilewidth").getAsInt());
            world.setVersion(jsonObject.get("version").getAsInt());
            world.setOrientation(getOrientation(jsonObject));
            world.setRenderOrder(getRenderOrder(jsonObject));
            world.setTileSets(getTileSets(jsonObject, jsonDeserializationContext));
            world.setLayers(getLayers(jsonObject, jsonDeserializationContext));
            world.setTiles(getTiles(world.getTileSets()));
            LOGGER.info("Deserialized world [{}]", world);
            return world;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize world.", e);
            throw new RuntimeException(e);
        }
    }

    private RenderOrder getRenderOrder(JsonObject jsonObject) {
        String renderOrder = jsonObject.get("renderorder").getAsString();
        return RenderOrder.valueOf(renderOrder.toUpperCase().replaceAll("-", "_"));
    }

    private Orientation getOrientation(JsonObject jsonObject) {
        String orientation = jsonObject.get("orientation").getAsString();
        return Orientation.valueOf(orientation.toUpperCase().replaceAll("-", "_"));
    }

    private List<Layer> getLayers(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        JsonArray layersJsonArray = jsonObject.get("layers").getAsJsonArray();
        Layer[] layers = jsonDeserializationContext.deserialize(layersJsonArray, Layer[].class);
        return Arrays.asList(layers);
    }

    private List<TileSet> getTileSets(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        JsonArray tileSetsJsonArray = jsonObject.get("tilesets").getAsJsonArray();
        TileSet[] tileSets = jsonDeserializationContext.deserialize(tileSetsJsonArray, TileSet[].class);
        return Arrays.asList(tileSets);
    }

    private Map<Integer, Tile> getTiles(List<TileSet> tileSets) {
        Map<Integer, Tile> tiles = new HashMap<>();
        for (TileSet tileSet : tileSets) {
            int globalId = tileSet.getFirstGlobalId();
            for (Tile tile : tileSet.getTiles()) {
                tiles.put(globalId, tile);
                globalId++;
            }
        }
        return tiles;
    }

}