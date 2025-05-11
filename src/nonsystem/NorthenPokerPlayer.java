package nonsystem;

import gamecore.NorthernPokerGame;
import gameenum.Status;
import gameinterface.Action;

import java.util.ArrayList;
import java.util.Scanner;

public class NorthenPokerPlayer extends Player{
    private final Scanner scanner = new Scanner(System.in);
    public NorthenPokerPlayer() {
        super();
    }
    public NorthenPokerPlayer(String name) {
        super(name);
    }

    public void playATurn() {
        if(!NorthernPokerGame.PokerField.isEmpty()) NorthernPokerGame.showPokerField();
        showCardOnHand();
        ArrayList<Card> cardsToPlay = chooseCards();
        if(cardsToPlay == null) pass();
        else {
            for(Card card : cardsToPlay) {
                hand.remove(card);
            }
        }

        NorthernPokerGame.PokerField.add(cardsToPlay);
        Action.clearScreen();
        NorthernPokerGame.showPokerField();
        showCardOnHand();
    }

    public void pass() {
        setState(Status.WAIT);
    }

    public ArrayList<Card> chooseCards() {
        System.out.println("Enter 1, 2, 3, 4, ..., " + hand.size() + " for card choosing: \n" +
                "(Several cards should be differ by space)\n" +
                "(Enter 0 if you want to pass.");

        ArrayList<Card> cardChosen = new ArrayList<>();
        String str = scanner.nextLine();

        if(str.equals("0")) return null;
        for(String s : str.split(" ")){
            cardChosen.add(hand.get(Integer.parseInt(s)));
        }

        return cardChosen;
    }

    public void showCardOnHand() {
        for(Card card : hand) {
            System.out.print(card.toString() + " ");
        }
        System.out.println();
    }
}
