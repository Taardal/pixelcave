package no.taardal.blossom;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.gender.Gender;
import no.taardal.blossom.service.ResourceService;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.world.World;

public class NagaBuilder implements Builder<Naga> {

    private ResourceService resourceService;
    private World world;
    private Gender gender;
    private String appearance;

    public NagaBuilder(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public Naga build() {
        String spriteSheetName = "naga-" + gender.toString().toLowerCase() + "-sheet-" + appearance;
        SpriteSheet spriteSheet = resourceService.getSpriteSheet().category("naga").name(spriteSheetName).spriteWidth(60).spriteHeight(60).build();
        return new Naga(world, spriteSheet);
    }

    public NagaBuilder world(World world) {
        this.world = world;
        return this;
    }

    public NagaBuilder gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public NagaBuilder appearance(String appearance) {
        this.appearance = appearance;
        return this;
    }

}
