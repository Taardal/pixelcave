package no.taardal.pixelcave.level;

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

}
