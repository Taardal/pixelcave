package no.taardal.blossom.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import no.taardal.blossom.game.Game;
import no.taardal.blossom.gamestate.BlossomGameStateManager;
import no.taardal.blossom.gamestate.GameStateManager;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.listener.ExitListener;
import no.taardal.blossom.listener.GameLoopListener;
import no.taardal.blossom.gameloop.BlossomGameLoop;
import no.taardal.blossom.gameloop.GameLoop;
import no.taardal.blossom.service.Service;
import no.taardal.blossom.service.TiledEditorLevelService;

public class GameModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(GameLoop.class).to(BlossomGameLoop.class);
        binder.bind(GameLoopListener.class).to(Game.class);
        binder.bind(GameStateManager.class).to(BlossomGameStateManager.class);
        binder.bind(ExitListener.class).to(Game.class);
        binder.bind(new LevelServiceTypeLiteral()).to(TiledEditorLevelService.class);
    }

    private class LevelServiceTypeLiteral extends TypeLiteral<Service<Level>> {
    }

}
