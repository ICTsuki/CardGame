package gamecore;

import gameenum.GameType;
import nonsystem.HostPlayer;
import nonsystem.Player;

public class NorthernPokerGame extends Game{
    public NorthernPokerGame(HostPlayer player) {
        super(GameType.NorthernPoker, player);
    }
    public void deal() {
        while(deck.remainingCards() != 0) {
            Player current = players.poll();
            assert current != null : "require player to deal card!";
            current.receiveCard(deck.dealCard());
        }
    }
    public void startGame(){}
}
