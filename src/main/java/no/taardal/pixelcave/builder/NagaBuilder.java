package no.taardal.pixelcave.builder;

import no.taardal.pixelcave.actor.Naga;
import no.taardal.pixelcave.gender.Gender;
import no.taardal.pixelcave.service.GameAssetService;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.vector.Vector2d;
import no.taardal.pixelcave.world.World;

public class NagaBuilder implements Builder<Naga> {

    private GameAssetService gameAssetService;
    private World world;
    private String gender;
    private String theme;
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

    public NagaBuilder theme(String theme) {
        this.theme = theme;
        return this;
    }

    public NagaBuilder theme(Naga.Theme theme) {
        this.theme = theme.toString().toLowerCase();
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
        String path = "naga/naga-" + gender + "-sheet-" + theme + ".png";
        int spriteWidth = 60;
        int spriteHeight = 60;
        return gameAssetService.getSpriteSheet(path, spriteWidth, spriteHeight);
    }

}
