import gamecore.Game;
import gamecore.NorthernPokerGame;
import nonsystem.Player;

public class CardGame {
    public static void main(String[] args) {
        Player player1 = new Player("Bao");
        Player player2 = new Player("A Duc");
        Player player3 = new Player("Hung");
        Player player4 = new Player("Khoa");

        Game game = new NorthernPokerGame();
    }
}