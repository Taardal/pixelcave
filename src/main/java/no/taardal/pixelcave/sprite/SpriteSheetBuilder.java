package no.taardal.pixelcave.sprite;

import no.taardal.pixelcave.builder.Builder;
import no.taardal.pixelcave.service.ResourceService;

import java.awt.image.BufferedImage;

public class SpriteSheetBuilder implements Builder<SpriteSheet> {

    private static final String RESOURCE_PATH = "spritesheets";

    private ResourceService resourceService;
    private String fileName;
    private String fileType;
    private String directory;
    private String relativePath;
    private int spriteWidth;
    private int spriteHeight;

    public SpriteSheetBuilder(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public SpriteSheet build() {
        BufferedImage bufferedImage = resourceService.getImage(getPath());
        return new SpriteSheet(bufferedImage, spriteWidth, spriteHeight);
    }

    public SpriteSheetBuilder fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public SpriteSheetBuilder fileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SpriteSheetBuilder directory(String directory) {
        this.directory = directory;
        return this;
    }

    public SpriteSheetBuilder relativePath(String relativePath) {
        this.relativePath = relativePath;
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

    private String getPath() {
        if (relativePath != null && !relativePath.isEmpty()) {
            return RESOURCE_PATH + "/" + relativePath;
        } else {
            if (fileType == null || fileType.isEmpty()) {
                fileType = "png";
            }
            return RESOURCE_PATH + "/" + directory + "/" + fileName + "." + fileType;
        }
    }

}
