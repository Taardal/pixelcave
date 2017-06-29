package no.taardal.blossom.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import no.taardal.blossom.game.Game;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.listener.ExitListener;
import no.taardal.blossom.provider.LevelsProvider;
import no.taardal.blossom.service.ResourceFileService;
import no.taardal.blossom.service.ResourceService;

import java.util.List;

public class GameModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(ExitListener.class).to(Game.class);
        binder.bind(ResourceService.class).to(ResourceFileService.class);
        binder.bind(new TypeLiteral<List<Level>>(){}).toProvider(LevelsProvider.class).asEagerSingleton();
    }

}
