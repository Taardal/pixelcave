package no.taardal.blossom.builder;

import no.taardal.blossom.coordinate.XYCoordinate;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;

public class SpriteBuilder implements Builder<Sprite> {

    private SpriteSheet spriteSheet;
    private XYCoordinate xyCoordinate;
    private int width;
    private int height;

    public SpriteBuilder() {
        xyCoordinate = new XYCoordinate(0, 0);
    }

    public SpriteBuilder(SpriteSheet spriteSheet) {
        this();
        this.spriteSheet = spriteSheet;
    }

    @Override
    public Sprite build() {
        return spriteSheet.getSprite(xyCoordinate, width, height);
    }

    public SpriteBuilder atX(int x) {
        xyCoordinate.setX(x);
        return this;
    }

    public SpriteBuilder atY(int y) {
        xyCoordinate.setY(y);
        return this;
    }

    public SpriteBuilder atXYCoordinate(int x, int y) {
        xyCoordinate.setX(x);
        xyCoordinate.setY(y);
        return this;
    }

    public SpriteBuilder atXYCoordinate(XYCoordinate xyCoordinate) {
        this.xyCoordinate = xyCoordinate;
        return this;
    }

    public SpriteBuilder fromSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        return this;
    }

    public SpriteBuilder withWidth(int width) {
        this.width = width;
        return this;
    }

    public SpriteBuilder withHeight(int height) {
        this.height = height;
        return this;
    }
}
