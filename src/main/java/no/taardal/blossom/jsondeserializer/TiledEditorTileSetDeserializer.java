package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.resourceloader.ResourceLoader;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.tile.TiledEditorTileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.util.*;

public class TiledEditorTileSetDeserializer implements JsonDeserializer<TiledEditorTileSet> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorTileSetDeserializer.class);

    private ResourceLoader<BufferedImage> bufferedImageResourceLoader;

    public TiledEditorTileSetDeserializer(ResourceLoader<BufferedImage> bufferedImageResourceLoader) {
        this.bufferedImageResourceLoader = bufferedImageResourceLoader;
    }

    @Override
    public TiledEditorTileSet deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            TiledEditorTileSet tiledEditorTileSet = new TiledEditorTileSet();
            tiledEditorTileSet.setTiles(getTiles(jsonObject));
            JsonElement imageJsonObject = jsonObject.get("image");
            if (imageJsonObject != null && !imageJsonObject.isJsonNull()) {
                tiledEditorTileSet.setImagePath(imageJsonObject.getAsString());
                tiledEditorTileSet.setImageWidth(jsonObject.get("imagewidth").getAsInt());
                tiledEditorTileSet.setImageHeight(jsonObject.get("imageheight").getAsInt());
            }
            tiledEditorTileSet.setName(jsonObject.get("name").getAsString());
            tiledEditorTileSet.setFirstGlobalId(jsonObject.get("firstgid").getAsInt());
            tiledEditorTileSet.setNumberOfTiles(jsonObject.get("tilecount").getAsInt());
            tiledEditorTileSet.setNumberOfColumns(jsonObject.get("columns").getAsInt());
            tiledEditorTileSet.setTileWidth(jsonObject.get("tilewidth").getAsInt());
            tiledEditorTileSet.setTileHeight(jsonObject.get("tileheight").getAsInt());
            tiledEditorTileSet.setMargin(jsonObject.get("margin").getAsInt());
            tiledEditorTileSet.setSpacing(jsonObject.get("spacing").getAsInt());
            LOGGER.info("Deserialized tiled editor tile set [{}]", tiledEditorTileSet);
            return tiledEditorTileSet;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            throw new RuntimeException(e);
        }
    }

    private List<Tile> getTiles(JsonObject jsonObject) {
        Map<Integer, Tile> tiles = new TreeMap<>();
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.get("tiles").getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            Integer id = Integer.valueOf(entry.getKey());
            BufferedImage bufferedImage = getTileBufferedImage(entry.getValue());
            tiles.put(id, new Tile(bufferedImage));
        }
        return new ArrayList<>(tiles.values());
    }

    private BufferedImage getTileBufferedImage(JsonElement tileImageJsonElement) {
        String imagePath = tileImageJsonElement.getAsJsonObject().get("image").getAsString().replaceFirst("../", "");
        return bufferedImageResourceLoader.loadResource(imagePath);
    }

}
