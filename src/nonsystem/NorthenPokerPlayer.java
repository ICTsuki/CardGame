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

    private void spacing() {
        for(int i = 0; i < 2; i++) System.out.println();
    }

    public void playATurn() {
        spacing();
        if(!NorthernPokerGame.PokerField.isEmpty()) NorthernPokerGame.showPokerField();
        spacing();
        showInfo();
        System.out.println("Cards on hand: ");
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
    }

    public void pass() {
        setState(Status.WAIT);
    }

    public ArrayList<Card> chooseCards() {
        System.out.println("Enter 0, 1, 2, 3, 4, ..., " + (hand.size() - 1) + " for card choosing: \n" +
                "(Several cards should be differ by space)\n" +
                "(Enter -1 if you want to pass.)");

        ArrayList<Card> cardChosen = new ArrayList<>();
        String str = scanner.nextLine();

        if(str.equals("-1")) return null;
        for(String s : str.split(" ")){
            cardChosen.add(hand.get(Integer.parseInt(s)));
        }

        return cardChosen;
    }

    public void showCardOnHand() {
        int i = 0;
        for(Card card : hand) {
            System.out.println(i + ": " + card.toString());
            i++;
        }
        System.out.println();
    }
}
