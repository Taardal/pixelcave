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
            tiledEditorTileSet.setName(jsonObject.get("name").getAsString());
            tiledEditorTileSet.setFirstGlobalId(jsonObject.get("firstgid").getAsInt());
            tiledEditorTileSet.setNumberOfTiles(jsonObject.get("tilecount").getAsInt());
            tiledEditorTileSet.setNumberOfColumns(jsonObject.get("columns").getAsInt());
            tiledEditorTileSet.setTileWidth(jsonObject.get("tilewidth").getAsInt());
            tiledEditorTileSet.setTileHeight(jsonObject.get("tileheight").getAsInt());
            tiledEditorTileSet.setMargin(jsonObject.get("margin").getAsInt());
            tiledEditorTileSet.setSpacing(jsonObject.get("spacing").getAsInt());
            JsonElement imageJsonElement = jsonObject.get("image");
            if (imageJsonElement != null && !imageJsonElement.isJsonNull()) {
                String imagePath = imageJsonElement.getAsString().replaceFirst("../", "");
                tiledEditorTileSet.setImagePath(imagePath);
                tiledEditorTileSet.setImageWidth(jsonObject.get("imagewidth").getAsInt());
                tiledEditorTileSet.setImageHeight(jsonObject.get("imageheight").getAsInt());
                List<Tile> tiles = getTiles(imagePath, tiledEditorTileSet.getTileWidth(), tiledEditorTileSet.getTileHeight());
                tiledEditorTileSet.setTiles(tiles);
            } else {
                tiledEditorTileSet.setTiles(getTiles(jsonObject));
            }
            LOGGER.info("Deserialized tiled editor tile set [{}]", tiledEditorTileSet);
            return tiledEditorTileSet;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            throw new RuntimeException(e);
        }
    }

    private List<Tile> getTiles(String imagePath, int tileWidth, int tileHeight) {
        List<Tile> tiles = new ArrayList<>();
        BufferedImage bufferedImage = bufferedImageResourceLoader.loadResource(imagePath);
        int numberOfTilesY = bufferedImage.getHeight() / tileHeight;
        int numberOfTilesX = bufferedImage.getWidth() / tileWidth;
        for (int y = 0; y < numberOfTilesY; y++) {
            for (int x = 0; x < numberOfTilesX; x++) {
                BufferedImage subImage = bufferedImage.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                tiles.add(new Tile(subImage));
            }
        }
        return tiles;
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

    private BufferedImage getTileBufferedImage(JsonElement jsonElement) {
        String imagePath = jsonElement.getAsJsonObject().get("image").getAsString().replaceFirst("../", "");
        return bufferedImageResourceLoader.loadResource(imagePath);
    }

}
