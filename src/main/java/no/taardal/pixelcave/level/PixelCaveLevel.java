package no.taardal.pixelcave.level;

import no.taardal.pixelcave.actor.Enemy;
import no.taardal.pixelcave.builder.NagaBuilder;
import no.taardal.pixelcave.gameobject.GameObject;
import no.taardal.pixelcave.ribbon.RibbonManager;
import no.taardal.pixelcave.service.GameAssetService;
import no.taardal.pixelcave.world.World;

public class PixelCaveLevel extends Level {

    public PixelCaveLevel(GameAssetService gameAssetService) {
        super(gameAssetService);
    }

    @Override
    protected World getWorld(GameAssetService gameAssetService) {
        return gameAssetService.getWorld("world_pixelcave.json");
    }

    @Override
    protected RibbonManager getRibbonManager(GameAssetService gameAssetService) {
        return new RibbonManager(gameAssetService.getRibbons(""));
    }

    @Override
    protected Enemy getEnemy(GameObject actorGameObject, GameAssetService gameAssetService) {
        if (actorGameObject.getName().equals("naga")) {
            return getNaga(actorGameObject, gameAssetService);
        } else {
            return null;
        }
    }

    private Enemy getNaga(GameObject actorGameObject, GameAssetService gameAssetService) {
        return new NagaBuilder(gameAssetService)
                .gender((String) actorGameObject.getProperties().get("gender"))
                .theme((String) actorGameObject.getProperties().get("theme"))
                .position(actorGameObject.getX(), actorGameObject.getY())
                .build();
    }

}
