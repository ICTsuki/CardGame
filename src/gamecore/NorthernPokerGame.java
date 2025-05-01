package gamecore;

import gameenum.GameType;
import nonsystem.HostPlayer;
import nonsystem.Player;

public class NorthernPokerGame extends Game{
    public NorthernPokerGame(HostPlayer player) {
        super(GameType.NorthernPoker, player);
    }
    public void deal() {
        while(deck.remainingCards() != 52 - players.size()*13) {
            Player current = players.poll();
            assert current != null : "require player to deal card!";
            current.receiveCard(deck.dealCard());
        }
    }
    public void startGame(){
        deck.shuffle();
        deal();
        Player current = players.peek();
        while(current.getCardsAmount() != 0) {
            current.playATurn();
            current = players.poll();
        }
    }
}
