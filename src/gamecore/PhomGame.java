package gamecore;

import nonsystem.Deck;
import nonsystem.Player;

public class PhomGame extends Game{
    public int PHOMMAXTURN = players.size() * 4;
    private int round = 0;

    public PhomGame() {
        super();
    }

    public void deal() {
        while(deck.remainingCards() >= 52 - players.size() * 9) {
            Player current = players.poll();
            assert current != null : "Require player to deal card!";
            if(current.getHand().size() <= 9) {
                current.receiveCard(deck.dealCard());
                players.offer(current);
            }
        }
    }

    public void startGame() {
        deck.shuffle();
        deal();
        Player current = players.peek();

        while(round <= 4) {
            current.playATurn();
            current = players.poll();
        }

        end();
    }

    public void addPlayer(Player player) {
        if(players.size() + 1 > MAXPLAYER) {
            System.out.println("Player full!");
            return;
        }
        players.add(player);
    }

    public Deck getDeck() {
        return deck;
    }

    private void end() {
        while(!players.isEmpty()) {
            Player current = players.peek();
            current.release();
            players.remove(current);
        }
    }

    public static void showPhomField() {

    }
}
