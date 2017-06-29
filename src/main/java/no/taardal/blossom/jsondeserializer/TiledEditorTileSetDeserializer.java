package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.service.ResourceService;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.tile.TileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.util.*;

public class TiledEditorTileSetDeserializer implements JsonDeserializer<TileSet> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorTileSetDeserializer.class);

    private ResourceService resourceService;

    public TiledEditorTileSetDeserializer(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public TileSet deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            TileSet tileSet = new TileSet();
            tileSet.setName(jsonObject.get("name").getAsString());
            tileSet.setFirstGlobalId(jsonObject.get("firstgid").getAsInt());
            tileSet.setNumberOfTiles(jsonObject.get("tilecount").getAsInt());
            tileSet.setNumberOfColumns(jsonObject.get("columns").getAsInt());
            tileSet.setTileWidth(jsonObject.get("tilewidth").getAsInt());
            tileSet.setTileHeight(jsonObject.get("tileheight").getAsInt());
            tileSet.setMargin(jsonObject.get("margin").getAsInt());
            tileSet.setSpacing(jsonObject.get("spacing").getAsInt());
            JsonElement imageJsonElement = jsonObject.get("image");
            if (imageJsonElement != null && !imageJsonElement.isJsonNull()) {
                String imagePath = imageJsonElement.getAsString().replaceFirst("../", "");
                tileSet.setImagePath(imagePath);
                tileSet.setImageWidth(jsonObject.get("imagewidth").getAsInt());
                tileSet.setImageHeight(jsonObject.get("imageheight").getAsInt());
                tileSet.setTiles(getTiles(imagePath, tileSet.getTileWidth(), tileSet.getTileHeight()));
            } else {
                tileSet.setTiles(getTiles(jsonObject));
            }
            LOGGER.info("Deserialized tile set [{}]", tileSet);
            return tileSet;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            throw new RuntimeException(e);
        }
    }

    private List<Tile> getTiles(String imagePath, int tileWidth, int tileHeight) {
        List<Tile> tiles = new ArrayList<>();
        BufferedImage bufferedImage = resourceService.getImage(imagePath);
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
        return resourceService.getImage(imagePath);
    }

}
