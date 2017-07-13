package no.taardal.blossom.sprite;

import no.taardal.blossom.Builder;
import no.taardal.blossom.service.ResourceFileService;
import no.taardal.blossom.service.ResourceService;

import java.awt.image.BufferedImage;

public class SpriteSheetBuilder implements Builder<SpriteSheet> {

    private static final String SPRITE_SHEET_PATH = "spritesheets";

    private ResourceService resourceService;
    private String directory;
    private String fileName;
    private int spriteWidth;
    private int spriteHeight;

    public SpriteSheetBuilder() {
        resourceService = new ResourceFileService();
        directory = "";
        fileName = "";
        spriteWidth = 0;
        spriteHeight = 0;
    }

    @Override
    public SpriteSheet build() {
        String path = SPRITE_SHEET_PATH + "/" + directory + "/" + fileName;
        BufferedImage bufferedImage = resourceService.getImage(path);
        return new SpriteSheet(bufferedImage, spriteWidth, spriteHeight);
    }

    public SpriteSheetBuilder directory(String directory) {
        this.directory = directory;
        return this;
    }

    public SpriteSheetBuilder fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public SpriteSheetBuilder spriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
        return this;
    }

    public SpriteSheetBuilder spriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
        return this;
    }

}
