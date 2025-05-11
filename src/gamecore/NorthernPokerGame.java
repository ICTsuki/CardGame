package gamecore;

import gameenum.Status;
import nonsystem.Card;
import nonsystem.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NorthernPokerGame extends Game{
    public static final List<ArrayList<Card>> PokerField = new ArrayList<>();

    public NorthernPokerGame() {
        super();
    }
    public void deal() {
        while(deck.remainingCards() != 52 - players.size()*13 && !players.isEmpty()) {
            Player current = players.poll();
            Objects.requireNonNull(current).receiveCard(deck.dealCard());
        }
    }

    public void startGame(){
        if(!players.isEmpty()){
            deck.shuffle();
            deal();
            Player current = players.peek();
            while(Objects.requireNonNull(current).getHand() != null && current.getState() != Status.WAIT) {
                current.playATurn();
                current = players.poll();
            }

        }
        else {
            System.out.println("There're no player");
        }
    }

    public void addPlayer(Player player) {
        if(players.size() + 1 > MAXPLAYER) {
            System.out.println("Player full!");
            return;
        }
        players.add(player);
    }

    public static void showPokerField() {
        for(Card card : PokerField.getLast()) {
            System.out.print(card.toString());
        }
        System.out.println();
    }
}
