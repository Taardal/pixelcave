package no.taardal.blossom.sprite;

import no.taardal.blossom.Builder;
import no.taardal.blossom.service.FileService;

import java.awt.image.BufferedImage;

public class SpriteSheetBuilder implements Builder<SpriteSheet> {

    private static final String SPRITE_SHEET_PATH = "spritesheets";

    private FileService fileService;
    private String name;
    private String type;
    private int spriteWidth;
    private int spriteHeight;

    public SpriteSheetBuilder(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public SpriteSheet build() {
        String path = SPRITE_SHEET_PATH + "/" + type + "/" + name;
        BufferedImage bufferedImage = fileService.getImage(path);
        return new SpriteSheet(bufferedImage, spriteWidth, spriteHeight);
    }

    public SpriteSheetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SpriteSheetBuilder category(String type) {
        this.type = type;
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
