package no.taardal.blossom.builder;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.service.GameAssetService;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class KnightBuilder implements Builder<Knight> {

    private GameAssetService gameAssetService;
    private World world;
    private String theme;
    private Vector2d position;

    public KnightBuilder(GameAssetService gameAssetService) {
        this.gameAssetService = gameAssetService;
    }

    @Override
    public Knight build() {
        Knight knight = new Knight(getSpriteSheet());
        knight.setWorld(world);
        knight.setPosition(position);
        return knight;
    }

    public KnightBuilder world(World world) {
        this.world = world;
        return this;
    }

    public KnightBuilder theme(String theme) {
        this.theme = theme;
        return this;
    }

    public KnightBuilder theme(Knight.Theme theme) {
        this.theme = theme.toString().toLowerCase();
        return this;
    }

    public KnightBuilder position(Vector2d position) {
        this.position = position;
        return this;
    }

    public KnightBuilder position(double x, double y) {
        this.position = new Vector2d(x, y);
        return this;
    }

    private SpriteSheet getSpriteSheet() {
        String path = "knight/spritesheet-knight-" + theme + ".png";
        int spriteWidth = 40;
        int spriteHeight = 40;
        return gameAssetService.getSpriteSheet(path, spriteWidth, spriteHeight);
    }


}
