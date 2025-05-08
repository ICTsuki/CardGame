package nonsystem;

import gamecore.NorthernPokerGame;

import java.util.ArrayList;

public class NorthenPokerPlayer extends Player{

    public NorthenPokerPlayer() {
        super();
    }
    public NorthenPokerPlayer(String name) {
        super(name);
    }

    public void playATurn() {
        ArrayList<Card> CardsToPlay = chooseCards();
        hand.remove(CardsToPlay);
        NorthernPokerGame.PokerField.add(CardsToPlay);

    }

    public ArrayList<Card> chooseCards() {
        return null;
    }
}
