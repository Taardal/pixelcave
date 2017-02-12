package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.tile.TiledEditorTileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class TiledEditorTileSetsDeserializer implements JsonDeserializer<TiledEditorTileSet[]> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorTileSetsDeserializer.class);

    @Override
    public TiledEditorTileSet[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            TiledEditorTileSet[] tiledEditorTileSets = new TiledEditorTileSet[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                tiledEditorTileSets[i] = jsonDeserializationContext.deserialize(jsonArray.get(i), TiledEditorTileSet.class);
            }
            LOGGER.info("Deserialized [{}] tiled editor tile sets.", tiledEditorTileSets.length);
            return tiledEditorTileSets;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer json array.", e);
            throw new RuntimeException(e);
        }
    }

}
