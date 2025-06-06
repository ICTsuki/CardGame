package main.java.game.gamecore;

import main.java.game.gameenum.Rank;
import main.java.game.gameenum.Status;
import main.java.game.gameenum.Suit;
import main.java.game.nonsystem.Card;
import main.java.game.nonsystem.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SouthernPokerGame extends Game{
    private static SouthernPokerGame instance;


    public static final List<ArrayList<Card>> PokerField = new ArrayList<>();

    private SouthernPokerGame() {
        super();
    }

    public static SouthernPokerGame getInstance() {
        if(instance == null) {
            instance = new SouthernPokerGame();
        }
        return instance;
    }



    public void deal() {
        while(deck.remainingCards() != 52 - players.size()*13 && !players.isEmpty()) {
            Player current = players.poll();
            Objects.requireNonNull(current).receiveCard(deck.dealCard());
            players.add(current);
        }

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
            System.out.println("End game");

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
