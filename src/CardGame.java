import gamecore.Game;
import gamecore.NorthernPokerGame;
import nonsystem.HostPlayer;
import nonsystem.Player;

public class CardGame {
    public static void main(String[] args) {
        HostPlayer player1 = new HostPlayer("Bao");
        Player player2 = new Player("A Duc");
        Player player3 = new Player("Hung");
        Player player4 = new Player("Khoa");

        Game game = new NorthernPokerGame(player1);
    }
}