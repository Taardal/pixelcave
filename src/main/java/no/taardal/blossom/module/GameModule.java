package no.taardal.blossom.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import no.taardal.blossom.game.Game;
import no.taardal.blossom.jsondeserializer.*;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.listener.ExitListener;
import no.taardal.blossom.provider.LevelsProvider;
import no.taardal.blossom.service.GameAssetService;
import no.taardal.blossom.service.LocalGameAssetService;
import no.taardal.blossom.service.LocalResourceService;
import no.taardal.blossom.service.ResourceService;
import no.taardal.blossom.tile.TileSet;
import no.taardal.blossom.world.World;

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
