package no.taardal.pixelcave.service;

import com.google.gson.Gson;
import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.world.World;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LocalGameAssetService implements GameAssetService {

    private static final String WORLDS_RESOURCE_PATH = "worlds";
    private static final String RIBBONS_RESOURCE_PATH = "ribbons";
    private static final String SPRITE_SHEET_RESOURCE_PATH = "spritesheets";

    private ResourceService resourceService;
    private Gson gson;

    @Inject
    public LocalGameAssetService(ResourceService resourceService, Gson gson) {
        this.resourceService = resourceService;
        this.gson = gson;
    }

    @Override
    public World getWorld(String relativePath) {
        String path = WORLDS_RESOURCE_PATH + "/" + relativePath;
        return gson.fromJson(resourceService.readFile(path), World.class);
    }

    @Override
    public List<Ribbon> getRibbons(String relativePath) {
        List<Ribbon> ribbons = new ArrayList<>();
        String directoryPath = RIBBONS_RESOURCE_PATH + "/" + relativePath;
        for (String fileName : resourceService.getFileNames(directoryPath)) {
            BufferedImage bufferedImage = resourceService.getImage(directoryPath + "/" + fileName);
            ribbons.add(new Ribbon(bufferedImage));
        }
        return ribbons;
    }

    @Override
    public SpriteSheet getSpriteSheet(String relativePath, int spriteWidth, int spriteHeight) {
        String path = SPRITE_SHEET_RESOURCE_PATH + "/" + relativePath;
        BufferedImage bufferedImage = resourceService.getImage(path);
        //return new SpriteSheet(bufferedImage, spriteWidth, spriteHeight);
        return null;
    }

}
