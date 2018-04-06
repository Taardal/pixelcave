package no.taardal.pixelcave.provider;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.service.ResourceService;

import java.util.ArrayList;
import java.util.List;

public class LevelsProvider implements Provider<Level[]> {

    private ResourceService resourceService;
    private Gson gson;

    @Inject
    public LevelsProvider(ResourceService resourceService, Gson gson) {
        this.resourceService = resourceService;
        this.gson = gson;
    }

    @Override
    public Level[] get() {
        List<Level> levels = new ArrayList<>();
        levels.add(new Level(resourceService, gson));
        return levels.toArray(new Level[0]);
    }

}