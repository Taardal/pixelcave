package no.taardal.pixelcave.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import no.taardal.pixelcave.game.Game;
import no.taardal.pixelcave.jsondeserializer.*;
import no.taardal.pixelcave.layer.Layer;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.listener.ExitListener;
import no.taardal.pixelcave.provider.LevelsProvider;
import no.taardal.pixelcave.service.GameAssetService;
import no.taardal.pixelcave.service.LocalGameAssetService;
import no.taardal.pixelcave.service.LocalResourceService;
import no.taardal.pixelcave.service.ResourceService;
import no.taardal.pixelcave.tile.TileSet;
import no.taardal.pixelcave.world.World;

public class GameModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(ExitListener.class).to(Game.class);
        binder.bind(GameAssetService.class).to(LocalGameAssetService.class);
        binder.bind(ResourceService.class).to(LocalResourceService.class);
        binder.bind(Level[].class).toProvider(LevelsProvider.class).asEagerSingleton();
    }

    @Provides
    public Gson provideGson(ResourceService resourceService) {
        return new GsonBuilder()
                .registerTypeAdapter(World.class, new TiledEditorWorldDeserializer())
                .registerTypeAdapter(TileSet[].class, new TiledEditorTileSetsDeserializer())
                .registerTypeAdapter(TileSet.class, new TiledEditorTileSetDeserializer(resourceService))
                .registerTypeAdapter(Layer[].class, new TiledEditorLayersDeserializer())
                .registerTypeAdapter(Layer.class, new TiledEditorLayerDeserializer())
                .create();
    }

}
