package no.taardal.blossom.level;

import no.taardal.blossom.actor.Enemy;
import no.taardal.blossom.builder.NagaBuilder;
import no.taardal.blossom.gameobject.GameObject;
import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.service.GameAssetService;
import no.taardal.blossom.world.World;

public class PixelCaveLevel extends Level {

    public PixelCaveLevel(GameAssetService gameAssetService) {
        super(gameAssetService);
    }

    @Override
    protected World getWorld(GameAssetService gameAssetService) {
        return gameAssetService.getWorld("pixelcave.json");
    }

    @Override
    protected RibbonManager getRibbonManager(GameAssetService gameAssetService) {
        return new RibbonManager(gameAssetService.getRibbons("pixelcave"));
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
