package no.taardal.blossom.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Provider;
import no.taardal.blossom.jsondeserializer.*;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.ribbon.Ribbon;
import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.service.ResourceService;
import no.taardal.blossom.tile.TileSet;
import no.taardal.blossom.world.World;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LevelsProvider implements Provider<List<Level>> {

    private static final String WORLDS_RESOURCE_PATH = "worlds";
    private static final String RIBBONS_RESOURCE_PATH = "ribbons";

    private ResourceService resourceService;
    private Gson gson;
    private List<Level> levels;

    @Inject
    public LevelsProvider(ResourceService resourceService) {
        this.resourceService = resourceService;
        gson = getGson();
        levels = getLevels();
    }

    @Override
    public List<Level> get() {
        return levels;
    }

    private List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();
        levels.add(getLevel("pixelcave"));
        return levels;
    }

    private Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(World.class, new TiledEditorWorldDeserializer())
                .registerTypeAdapter(TileSet[].class, new TiledEditorTileSetsDeserializer())
                .registerTypeAdapter(TileSet.class, new TiledEditorTileSetDeserializer(resourceService))
                .registerTypeAdapter(Layer[].class, new TiledEditorLayersDeserializer())
                .registerTypeAdapter(Layer.class, new TiledEditorLayerDeserializer())
                .create();
    }

    private Level getLevel(String name) {
        return new Level(getWorld(name), new RibbonManager(getRibbons(name)));
    }

    private World getWorld(String name) {
        String path = WORLDS_RESOURCE_PATH + "/" + name + ".json";
        return gson.fromJson(resourceService.readFile(path), World.class);
    }

    private List<Ribbon> getRibbons(String name) {
        List<Ribbon> ribbons = new ArrayList<>();
        String directoryPath = RIBBONS_RESOURCE_PATH + "/" + name;
        for (String fileName : resourceService.getFileNames(directoryPath)) {
            BufferedImage bufferedImage = resourceService.getImage(directoryPath + "/" + fileName);
            ribbons.add(new Ribbon(bufferedImage));
        }
        return ribbons;
    }

}
