import main.java.game.gamecore.ThreeCards;
import main.java.game.nonsystem.Player;

public class ThreeCardGame {
    public static void main(String[] args) {
        Player player1 = new Player("Hung");
        Player player2 = new Player("Bao");
        Player player3 = new Player("A Duc");
        Player player4 = new Player("Duong");
        Player player5 = new Player("Khoa");

        ThreeCards game = ThreeCards.getInstance();
        player1.joinGame(game);
        player2.joinGame(game);
        player3.joinGame(game);
        player4.joinGame(game);
        player5.joinGame(game);

        game.startGame();
    }
}
