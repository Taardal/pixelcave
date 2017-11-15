package no.taardal.pixelcave.builder;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.service.GameAssetService;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;

public class KnightBuilder implements Builder<Knight> {

    private GameAssetService gameAssetService;
    private World world;
    private String theme;
    private Vector2f position;

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

    public KnightBuilder position(Vector2f position) {
        this.position = position;
        return this;
    }

    public KnightBuilder position(float x, float y) {
        this.position = new Vector2f(x, y);
        return this;
    }

    private SpriteSheet getSpriteSheet() {
        String path = "knight/spritesheet-knight-" + theme + ".png";
        int spriteWidth = 40;
        int spriteHeight = 40;
        return gameAssetService.getSpriteSheet(path, spriteWidth, spriteHeight);
    }


}
