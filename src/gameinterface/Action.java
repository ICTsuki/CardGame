package gameinterface;

import nonsystem.Card;

import java.util.*;

public interface Action {
    void joinGame();
    void quitGame();
    void login();
    void logout();
    void inspectRules();
    void playATurn();

    static void clearScreen() {
        for (int i = 0; i < 50; i++) System.out.println();
    }


}
