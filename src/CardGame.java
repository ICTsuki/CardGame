import main.java.game.gamecore.SouthernPokerGame;
import main.java.game.nonsystem.SouthernPokerPlayer;


public class CardGame {
    public static void main(String[] args) {
        SouthernPokerPlayer player1 = new SouthernPokerPlayer("Bao");
        SouthernPokerPlayer player2 = new SouthernPokerPlayer("A Duc");
        SouthernPokerPlayer player3 = new SouthernPokerPlayer("Hung");
        SouthernPokerPlayer player4 = new SouthernPokerPlayer("Khoa");

        SouthernPokerGame game = SouthernPokerGame.getInstance();
        player2.joinGame(game);
        player1.joinGame(game);
        player3.joinGame(game);
        player4.joinGame(game);

        game.startGame();
    }
}