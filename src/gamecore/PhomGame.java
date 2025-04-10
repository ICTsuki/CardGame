package gamecore;

import gameenum.GameType;
import nonsystem.HostPlayer;
import nonsystem.Player;

public class PhomGame extends Game{
    public static int PHOMMAXTURN = players.size() * 4;
    public PhomGame(HostPlayer player) {
        super(GameType.Phom, player);
    }

    public void deal() {
        while(!players.isEmpty()) {
            Player current = players.poll();
            assert current != null : "Require player to deal card!";
            if(current.getCardsAmount() <= 9) {
                current.receiveCard(deck.dealCard());
                players.offer(current);
            }
        }
    }

    public void startGame() {
        deck.shuffle();
        deal();

    }
}
