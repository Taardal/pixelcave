package no.taardal.pixelcave.builder;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.service.GameAssetService;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;

public class KnightBuilder implements Builder<Knight> {

    private GameAssetService gameAssetService;
    private SpriteSheet spriteSheet;
    private World world;
    private String theme;
    private Vector2f position;
    private Vector2f velocity;

    public KnightBuilder(GameAssetService gameAssetService) {
        this.gameAssetService = gameAssetService;
    }

    @Override
    public Knight build() {
        if (spriteSheet == null) {
            spriteSheet = getSpriteSheet();
        }
        return new Knight(spriteSheet, world, position, velocity);
    }

    public KnightBuilder spriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        return this;
    }

    public KnightBuilder world(World world) {
        this.world = world;
        return this;
    }

    public KnightBuilder theme(String theme) {
        this.theme = theme.toLowerCase();
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

    public KnightBuilder x(float x) {
        if (position != null) {
            position = position.withX(x);
        } else {
            position = new Vector2f(x, 0);
        }
        return this;
    }

    public KnightBuilder y(float y) {
        if (position != null) {
            position = position.withY(y);
        } else {
            position = new Vector2f(0, y);
        }
        return this;
    }

    public KnightBuilder velocity(Vector2f velocity) {
        this.velocity = velocity;
        return this;
    }

    private SpriteSheet getSpriteSheet() {
        String path = "knight/spritesheet-knight-" + theme + ".png";
        return gameAssetService.getSpriteSheet(path, Knight.SPRITE_WIDTH, Knight.SPRITE_HEIGHT);
    }


}
