package no.taardal.blossom.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.level.TiledEditorLevel;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.ribbon.Ribbon;
import no.taardal.blossom.service.Service;

import java.util.ArrayList;
import java.util.List;

public class TiledEditorLevelsProvider implements Provider<List<Level>> {

    private Service<TiledEditorMap> tiledEditorMapService;
    private Service<List<Ribbon>> ribbonsService;
    private List<Level> levels;

    @Inject
    public TiledEditorLevelsProvider(Service<TiledEditorMap> tiledEditorMapService, Service<List<Ribbon>> ribbonsService) {
        this.tiledEditorMapService = tiledEditorMapService;
        this.ribbonsService = ribbonsService;
        levels = getLevels();
    }

    @Override
    public List<Level> get() {
        return levels;
    }

    private List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();
        levels.add(new TiledEditorLevel(tiledEditorMapService.get("pixelcave"), ribbonsService.get("pixelcave")));
        return levels;
    }

}
