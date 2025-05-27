package main.java.game.gameinterface;

import main.java.game.gamecore.Game;

public interface Action {
    void joinGame(Game game);
    void inspectRules();
    void playATurn();

    static void clearScreen() {
        for (int i = 0; i < 50; i++) System.out.println();
    }
}
