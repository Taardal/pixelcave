package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.layer.Layer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class TiledEditorLayersDeserializer implements JsonDeserializer<Layer[]> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorLayersDeserializer.class);

    @Override
    public Layer[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Layer[] layers = new Layer[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                layers[i] = jsonDeserializationContext.deserialize(jsonArray.get(i), Layer.class);
            }
            LOGGER.info("Deserialized [{}] layers.", layers.length);
            return layers;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layers json array.", e);
            throw new RuntimeException(e);
        }
    }

}
