package no.taardal.blossom;

import no.taardal.blossom.game.BlossomGame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        BlossomGame blossomGame = new BlossomGame();
        JFrame jframe = new JFrame();
        jframe.add(blossomGame.getGameCanvas());
        jframe.setTitle("Blossom");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.pack();
        blossomGame.start();
    }

}
