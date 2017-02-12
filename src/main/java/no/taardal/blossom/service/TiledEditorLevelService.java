package no.taardal.blossom.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import no.taardal.blossom.jsondeserializer.*;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.level.TiledEditorLevel;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.resourceloader.ResourceLoader;
import no.taardal.blossom.tile.TiledEditorTileSet;

import java.awt.image.BufferedImage;
import java.io.File;

public class TiledEditorLevelService implements Service<Level> {

    private static final String TILED_MAPS_RESOURCE_FOLDER = "tilededitormaps";
    private static final String JSON_FILE_TYPE = "json";

    private ResourceLoader<String> stringResourceLoader;
    private ResourceLoader<BufferedImage> bufferedImageResourceLoader;
    private Gson gson;

    @Inject
    public TiledEditorLevelService(ResourceLoader<String> stringResourceLoader, ResourceLoader<BufferedImage> bufferedImageResourceLoader) {
        this.stringResourceLoader = stringResourceLoader;
        this.bufferedImageResourceLoader = bufferedImageResourceLoader;
        gson = getGson();
    }

    @Override
    public Level get(String name) {
        String json = stringResourceLoader.loadResource(getResourcePath(name));
        TiledEditorMap tiledEditorMap = gson.fromJson(json, TiledEditorMap.class);
        return new TiledEditorLevel(tiledEditorMap);
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

    private String getResourcePath(String path) {
        if (!path.endsWith("." + JSON_FILE_TYPE)) {
            path += "." + JSON_FILE_TYPE;
        }
        return TILED_MAPS_RESOURCE_FOLDER + File.separator + path;
    }

}
