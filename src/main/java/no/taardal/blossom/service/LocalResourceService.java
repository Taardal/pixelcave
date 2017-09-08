package no.taardal.blossom.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.taardal.blossom.jsondeserializer.*;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.ribbon.Ribbon;
import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.sprite.SpriteSheetBuilder;
import no.taardal.blossom.tile.TileSet;
import no.taardal.blossom.world.World;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LocalResourceService implements ResourceService {

    private static final String WORLDS_RESOURCE_PATH = "worlds";
    private static final String RIBBONS_RESOURCE_PATH = "ribbons";
    private static final String SPRITE_SHEET_RESOURCE_PATH = "spritesheets";

    private FileService fileService;
    private Gson gson;

    public LocalResourceService() {
        fileService = new LocalFileService();
        gson = getGson();
    }

    @Override
    public World getWorld(String name) {
        String path = WORLDS_RESOURCE_PATH + "/" + name + ".json";
        return gson.fromJson(fileService.readFile(path), World.class);
    }

    @Override
    public RibbonManager getRibbonManager(String name) {
        return new RibbonManager(getRibbons(name));
    }

    @Override
    public SpriteSheetBuilder getSpriteSheet() {
        return new SpriteSheetBuilder(fileService);
    }

    private Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(World.class, new TiledEditorWorldDeserializer())
                .registerTypeAdapter(TileSet[].class, new TiledEditorTileSetsDeserializer())
                .registerTypeAdapter(TileSet.class, new TiledEditorTileSetDeserializer(fileService))
                .registerTypeAdapter(Layer[].class, new TiledEditorLayersDeserializer())
                .registerTypeAdapter(Layer.class, new TiledEditorLayerDeserializer())
                .create();
    }

    private List<Ribbon> getRibbons(String name) {
        List<Ribbon> ribbons = new ArrayList<>();
        String directoryPath = RIBBONS_RESOURCE_PATH + "/" + name;
        for (String fileName : fileService.getFileNames(directoryPath)) {
            BufferedImage bufferedImage = fileService.getImage(directoryPath + "/" + fileName);
            ribbons.add(new Ribbon(bufferedImage));
        }
        return ribbons;
    }

}
