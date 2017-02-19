package no.taardal.blossom.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import no.taardal.blossom.jsondeserializer.*;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.resourceloader.ResourceLoader;
import no.taardal.blossom.tile.TiledEditorTileSet;

import java.awt.image.BufferedImage;

public class TiledEditorMapService implements Service<TiledEditorMap> {

    private static final String TILED_MAPS_RESOURCE_FOLDER = "tilededitormaps";
    private static final String JSON_FILE_TYPE = "json";

    private ResourceLoader<String> stringResourceLoader;
    private ResourceLoader<BufferedImage> bufferedImageResourceLoader;
    private Gson gson;

    @Inject
    public TiledEditorMapService(ResourceLoader<String> stringResourceLoader, ResourceLoader<BufferedImage> bufferedImageResourceLoader) {
        this.stringResourceLoader = stringResourceLoader;
        this.bufferedImageResourceLoader = bufferedImageResourceLoader;
        gson = getGson();
    }

    @Override
    public TiledEditorMap get(String mapFileName) {
        String json = stringResourceLoader.loadResource(getResourcePath(mapFileName));
        return gson.fromJson(json, TiledEditorMap.class);
    }

    private Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(TiledEditorMap.class, new TiledEditorMapDeserializer())
                .registerTypeAdapter(TiledEditorTileSet[].class, new TiledEditorTileSetsDeserializer())
                .registerTypeAdapter(TiledEditorTileSet.class, new TiledEditorTileSetDeserializer(bufferedImageResourceLoader))
                .registerTypeAdapter(TiledEditorLayer[].class, new TiledEditorLayersDeserializer())
                .registerTypeAdapter(TiledEditorLayer.class, new TiledEditorLayerDeserializer())
                .create();
    }

    private String getResourcePath(String name) {
        if (!name.endsWith("." + JSON_FILE_TYPE)) {
            name += "." + JSON_FILE_TYPE;
        }
        return TILED_MAPS_RESOURCE_FOLDER + "/" + name;
    }

}
