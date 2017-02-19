package no.taardal.blossom.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.level.TiledEditorLevel;
import no.taardal.blossom.manager.RibbonsManager;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.service.Service;

import java.util.ArrayList;
import java.util.List;

public class TiledEditorLevelsProvider implements Provider<List<Level>> {

    private Service<TiledEditorMap> tiledEditorMapService;
    private Service<RibbonsManager> ribbonManagerService;
    private List<Level> levels;

    @Inject
    public TiledEditorLevelsProvider(Service<TiledEditorMap> tiledEditorMapService, Service<RibbonsManager> ribbonManagerService) {
        this.tiledEditorMapService = tiledEditorMapService;
        this.ribbonManagerService = ribbonManagerService;
        levels = getLevels();
    }

    @Override
    public List<Level> get() {
        return levels;
    }

    private List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();
        TiledEditorMap pixelCaveTiledEditorMap = tiledEditorMapService.get("pixelcave");
        RibbonsManager pixelCaveRibbonManager = ribbonManagerService.get("pixelcave");
        levels.add(new TiledEditorLevel(pixelCaveTiledEditorMap, pixelCaveRibbonManager));
        return levels;
    }

}
