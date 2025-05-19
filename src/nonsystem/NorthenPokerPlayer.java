package nonsystem;

import gamecore.NorthernPokerGame;
import gameenum.Status;
import gameinterface.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NorthenPokerPlayer extends Player {
    private final Scanner scanner = new Scanner(System.in);

    public NorthenPokerPlayer() {
        super();
    }

    public NorthenPokerPlayer(String name) {
        super(name);
    }

    private void spacing() {
        for (int i = 0; i < 1; i++) System.out.println();
    }

    public void playATurn() {
        spacing();
        if (!NorthernPokerGame.PokerField.isEmpty()) NorthernPokerGame.showPokerField();
        spacing();
        showInfo();
        System.out.println("Cards on hand: ");
        showCardOnHand();

        ArrayList<Card> cardsToPlay;
        while (!valid(cardsToPlay = chooseCards())) {
            System.out.println("Invalid cards!\n Please choose another cards:");
        }
        if (cardsToPlay == null) pass();
        else {
            for (Card card : cardsToPlay) {
                hand.remove(card);
            }
        }

        if (cardsToPlay != null) NorthernPokerGame.PokerField.add(cardsToPlay);
        NorthernPokerGame.previousPlayer = this;
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

        if (str.equals("-2")) {
            inspectRules();
            return chooseCards();
        }
        if (str.equals("-1")) return null;
        for (String s : str.split(" ")) { // 0 1 2
            cardChosen.add(hand.get(Integer.parseInt(s)));
        }

        return cardChosen;
    }

    public void showCardOnHand() {
        hand = Card.sortCard(hand);
        int i = 0;
        for (Card card : hand) {
            System.out.println(i + ": " + card.toString());
            i++;
        }
    }


    private boolean valid(List<Card> playCards) {
        List<Card> fieldCards = NorthernPokerGame.PokerField.getFirst();
        if(playCards == null) return true;

        if (NorthernPokerGame.previousPlayer.equals(this)) {
            int t = NorthernPokerGame.players.size();
            while (t-- > 0) {
                Player player = NorthernPokerGame.players.poll();
                if (player != null) {
                    player.setState(Status.READY);
                    NorthernPokerGame.players.add(player);
                }
                else break;
            }

            return playCards.size() == 1 ||
                    Card.doubleCombo(playCards) ||
                    Card.tripleCombo(playCards) ||
                    Card.straight(playCards);
            // Player continue to play since no other players can play back
        }

        if(NorthenPokerField.field.isEmpty()) {
            return playCards.size() == 1 ||
                    Card.doubleCombo(playCards) ||
                    Card.tripleCombo(playCards) ||
                    Card.straight(playCards);
        }

        return Card.sameSuits(playCards, fieldCards) &&
                Card.isBigger(playCards, fieldCards);
    }
}