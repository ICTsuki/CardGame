package nonsystem;

import gamecore.NorthernPokerGame;
import gameenum.Status;
import gameinterface.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

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
            // Player continue to play since no one can play back
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

    public void inspectRules(){
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Type 1 if you want English game rule and 0 if you want Vietnamese game rule");
            int t = sc.nextInt();

            if (t == 0) {
                File file = new File("gamerule/Northern Poker/Rule - VietNamese.txt");
                openTextFile(file);
                break;
            } else if (t == 1) {
                File file = new File("gamerule/Northern Poker/Rule - English.txt");
                openTextFile(file);
                break;
            } else {
                System.out.println("Please type as instructed!");
            }
        }

        sc.close();
    }

    private void openTextFile(File file) {
        if(!file.exists()) {
            System.out.println("File not found!");
            return;
        }

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        } else {
            System.out.println("Desktop not supported on this platform.");
        }
    }
}