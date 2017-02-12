package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.layer.TiledEditorLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class TiledEditorLayersDeserializer implements JsonDeserializer<TiledEditorLayer[]> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorLayersDeserializer.class);

    @Override
    public TiledEditorLayer[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            TiledEditorLayer[] tiledEditorLayers = new TiledEditorLayer[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                tiledEditorLayers[i] = jsonDeserializationContext.deserialize(jsonArray.get(i), TiledEditorLayer.class);
            }
            LOGGER.info("Deserialized [{}] tiled editor layers.", tiledEditorLayers.length);
            return tiledEditorLayers;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer json array.", e);
            throw new RuntimeException(e);
        }
    }

}
