package no.taardal.blossom;

import no.taardal.blossom.game.Game;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
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
