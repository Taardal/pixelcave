package no.taardal.blossom.level;

import no.taardal.blossom.NagaBuilder;
import no.taardal.blossom.actor.Enemy;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.gender.Gender;
import no.taardal.blossom.service.LocalResourceService;
import no.taardal.blossom.service.ResourceService;

import java.util.ArrayList;
import java.util.List;

public class CaveLevel extends Level {

    private static final String NAME = "pixelcave";

    public CaveLevel(LocalResourceService localResourceService) {
        super(NAME, localResourceService);
    }

    @Override
    protected List<Enemy> getEnemies(ResourceService resourceService) {
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new NagaBuilder(resourceService).gender(Gender.FEMALE).appearance("violet").world(world).build());
        return enemies;
    }

    @Override
    protected Player getPlayer(ResourceService resourceService) {
        return null;
    }

}
