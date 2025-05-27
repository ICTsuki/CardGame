package main.java.game.nonsystem;

import main.java.game.gamecore.Game;

public class Player extends Participant {

    public Player() {
        super();
    }

    public void showCardOnHand() {
        hand = Card.sortCardRank(hand);
        int i = 0;
        for (Card card : hand) {
            System.out.println(i + ": " + card.toString());
            i++;
        }
    }
    public Player(String name) {
        super(name);
    }
    public void joinGame(Game game) {
        game.addPlayer(this);
    }

}
