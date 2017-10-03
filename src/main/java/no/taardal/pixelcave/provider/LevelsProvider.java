package no.taardal.pixelcave.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.level.PixelCaveLevel;
import no.taardal.pixelcave.service.GameAssetService;

import java.util.ArrayList;
import java.util.List;

public class LevelsProvider implements Provider<Level[]> {

    private GameAssetService gameAssetService;

    @Inject
    public LevelsProvider(GameAssetService gameAssetService) {
        this.gameAssetService = gameAssetService;
    }

    @Override
    public Level[] get() {
        List<Level> levels = new ArrayList<>();
        levels.add(new PixelCaveLevel(gameAssetService));
        return levels.toArray(new Level[levels.size()]);
    }

}
