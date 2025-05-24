package client.game.gamecore;

import client.game.gameenum.Rank;
import client.game.gameenum.Status;
import client.game.gameenum.Suit;
import client.game.nonsystem.Card;
import client.game.nonsystem.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NorthernPokerGame extends Game{
    private static NorthernPokerGame instance;


    public static final List<ArrayList<Card>> PokerField = new ArrayList<>();
    public final Card firstPlayCard = new Card(Suit.SPADE, Rank.THREE);

    private NorthernPokerGame() {
        super();
    }

    public static NorthernPokerGame getInstance() {
        if(instance == null) {
            instance = new NorthernPokerGame();
        }
        return instance;
    }



    public void deal() {
        while(deck.remainingCards() != 52 - players.size()*13 && !players.isEmpty()) {
            Player current = players.poll();
            Objects.requireNonNull(current).receiveCard(deck.dealCard());
            players.add(current);
        }
        Player found = players.stream()
                        .filter(player -> player.getHand().contains(firstPlayCard))
                        .findFirst().orElse(null);

    }

    public void startGame(){
        if(!players.isEmpty()){
            showPlayer();
            deck.shuffle();
            deal();
            showPlayer();
            Player current = players.poll();
            if(current == null) System.out.println("initial null");
            while(current != null && current.getHand() != null) {
                if (current.getState() == Status.WAIT) {
                    players.add(current);
                    current = players.poll();
                    continue;
                }
                current.playATurn();
                players.add(current);
                current = players.poll();
            }
            if(current == null) System.out.println("Null player");
            else System.out.println("End game");

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
        System.out.println("Player added !");
        System.out.println("Current player: " + players.size());
    }

    public static void showPokerField() {
        for(Card card : PokerField.getLast()) {
            System.out.println(card.toString());
        }
        System.out.println();
    }

    public void showPlayer() {
        if(players.isEmpty()) {
            System.out.println("no player");
            return;
        }
        for(Player player : players) {
            player.showInfo();
        }
    }
}
