package no.taardal.blossom.builder;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.gender.Gender;
import no.taardal.blossom.service.GameAssetService;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class NagaBuilder implements Builder<Naga> {

    private GameAssetService gameAssetService;
    private World world;
    private String gender;
    private String variation;
    private Vector2d position;

    public NagaBuilder(GameAssetService gameAssetService) {
        this.gameAssetService = gameAssetService;
    }

    @Override
    public Naga build() {
        Naga naga = new Naga(getSpriteSheet());
        naga.setPosition(position);
        return naga;
    }

    public NagaBuilder world(World world) {
        this.world = world;
        return this;
    }

    public NagaBuilder gender(String gender) {
        this.gender = gender;
        return this;
    }

    public NagaBuilder gender(Gender gender) {
        this.gender = gender.toString().toLowerCase();
        return this;
    }

    public NagaBuilder variation(String variation) {
        this.variation = variation;
        return this;
    }

    public NagaBuilder variation(Naga.Variation variation) {
        this.variation = variation.toString().toLowerCase();
        return this;
    }

    public NagaBuilder position(Vector2d position) {
        this.position = position;
        return this;
    }

    public NagaBuilder position(double x, double y) {
        this.position = new Vector2d(x, y);
        return this;
    }

    private SpriteSheet getSpriteSheet() {
        String path = "naga/naga-" + gender + "-sheet1-" + variation + ".png";
        int spriteWidth = 60;
        int spriteHeight = 60;
        return gameAssetService.getSpriteSheet(path, spriteWidth, spriteHeight);
    }

}
