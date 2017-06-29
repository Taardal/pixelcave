package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.tile.TileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class TiledEditorTileSetsDeserializer implements JsonDeserializer<TileSet[]> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorTileSetsDeserializer.class);

    @Override
    public TileSet[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            TileSet[] tileSets = new TileSet[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                tileSets[i] = jsonDeserializationContext.deserialize(jsonArray.get(i), TileSet.class);
            }
            LOGGER.info("Deserialized [{}] tile sets.", tileSets.length);
            return tileSets;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize tile sets json array.", e);
            throw new RuntimeException(e);
        }
    }

}
