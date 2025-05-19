package gameinterface;

import gamecore.Game;

public interface Action {
    void joinGame(Game game);
    void quitGame();
    void login();
    void logout();
    void inspectRules();
    void playATurn();

    static void clearScreen() {
        for (int i = 0; i < 50; i++) System.out.println();
    }


}
