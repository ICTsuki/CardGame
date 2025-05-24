package client;

import client.game.gamecore.NorthernPokerGame;
import client.game.nonsystem.NorthernPokerPlayer;


public class CardGame {
    public static void main(String[] args) {
        NorthernPokerPlayer player1 = new NorthernPokerPlayer("Bao");
        NorthernPokerPlayer player2 = new NorthernPokerPlayer("A Duc");
        NorthernPokerPlayer player3 = new NorthernPokerPlayer("Hung");
        NorthernPokerPlayer player4 = new NorthernPokerPlayer("Khoa");

        NorthernPokerGame game = new NorthernPokerGame();
        player2.joinGame(game);
        player1.joinGame(game);
        player3.joinGame(game);
        player4.joinGame(game);

        game.startGame();
    }
}