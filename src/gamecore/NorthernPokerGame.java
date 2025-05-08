package gamecore;

import nonsystem.Card;
import nonsystem.Player;

import java.util.ArrayList;
import java.util.List;

public class NorthernPokerGame extends Game{
    public static List<ArrayList<Card>> PokerField = new ArrayList<>();

    public NorthernPokerGame() {
        super();
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

    public void addPlayer(Player player) {
        if(players.size() + 1 > MAXPLAYER) {
            System.out.println("Player full!");
            return;
        }
        players.add(player);
    }
}
