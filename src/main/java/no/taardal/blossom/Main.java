package no.taardal.blossom;

import com.google.inject.Guice;
import com.google.inject.Injector;
import no.taardal.blossom.game.Game;
import no.taardal.blossom.module.GameModule;
import no.taardal.blossom.module.ResourceModule;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GameModule(), new ResourceModule());
        Game game = injector.getInstance(Game.class);
        JFrame jframe = new JFrame();
        jframe.add(game);
        jframe.setTitle(Game.GAME_TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.pack();
        game.start();
    }

}
