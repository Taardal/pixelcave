package no.taardal.blossom.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import no.taardal.blossom.game.Game;
import no.taardal.blossom.state.gamestate.GameState;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.listener.ExitListener;
import no.taardal.blossom.manager.GameStateManager;
import no.taardal.blossom.manager.Manager;
import no.taardal.blossom.manager.RibbonsManager;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.provider.TiledEditorLevelsProvider;
import no.taardal.blossom.service.RibbonManagerService;
import no.taardal.blossom.service.Service;
import no.taardal.blossom.service.TiledEditorMapService;

import java.util.List;

public class GameModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(ExitListener.class).to(Game.class);
        binder.bind(new GameStateManagerTypeLiteral()).to(GameStateManager.class);
        binder.bind(new LevelsTypeLiteral()).toProvider(TiledEditorLevelsProvider.class);
        binder.bind(new TiledEditorMapServiceTypeLiteral()).to(TiledEditorMapService.class);
        binder.bind(new RibbonManagerServiceTypeLiteral()).to(RibbonManagerService.class);
    }

    private class GameStateManagerTypeLiteral extends TypeLiteral<Manager<GameState>> {

    }

    private class LevelsTypeLiteral extends TypeLiteral<List<Level>> {

    }

    private class TiledEditorMapServiceTypeLiteral extends TypeLiteral<Service<TiledEditorMap>> {

    }

    private class RibbonManagerServiceTypeLiteral extends TypeLiteral<Service<RibbonsManager>> {

    }
}
