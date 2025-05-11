import gamecore.Game;
import gamecore.NorthernPokerGame;
import nonsystem.NorthenPokerPlayer;
//import nonsystem.Player;

public class CardGame {
    public static void main(String[] args) {
        NorthenPokerPlayer player1 = new NorthenPokerPlayer("Bao");
        NorthenPokerPlayer player2 = new NorthenPokerPlayer("A Duc");
        NorthenPokerPlayer player3 = new NorthenPokerPlayer("Hung");
        NorthenPokerPlayer player4 = new NorthenPokerPlayer("Khoa");

        NorthernPokerGame game = new NorthernPokerGame();
        player2.joinGame(game);
        player1.joinGame(game);
        player3.joinGame(game);
        player4.joinGame(game);

        game.startGame();
    }
}