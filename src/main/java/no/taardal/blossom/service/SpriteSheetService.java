package no.taardal.blossom.service;

import no.taardal.blossom.resourceloader.ResourceLoader;
import no.taardal.blossom.sprite.SpriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class SpriteSheetService implements Service<SpriteSheet> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteSheetService.class);
    private static final String SPRITE_SHEET_RESOURCE_DIRECTORY = "spritesheets";

    private ResourceLoader<BufferedImage> bufferedImageResourceLoader;

    public SpriteSheetService(ResourceLoader<BufferedImage> bufferedImageResourceLoader) {
        this.bufferedImageResourceLoader = bufferedImageResourceLoader;
    }

    @Override
    public SpriteSheet get(String name) {
        String path = getPath(name);
        BufferedImage bufferedImage = bufferedImageResourceLoader.loadResource(path);
        SpriteSheet spriteSheet = new SpriteSheet(bufferedImage, 16, 16);
        return spriteSheet;
    }

    private String getPath(String name) {
        if (!name.startsWith(SPRITE_SHEET_RESOURCE_DIRECTORY)) {
            name = SPRITE_SHEET_RESOURCE_DIRECTORY + "/" + name;
        }
        return name;
    }

}
