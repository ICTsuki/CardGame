package main.java.game.nonsystem;

import main.java.game.gamecore.SouthernPokerGame;
import main.java.game.gameenum.Rank;
import main.java.game.gameenum.Status;
import main.java.game.gameenum.Suit;
import main.java.game.gameinterface.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import java.util.logging.Logger;
import java.util.logging.Level;

import static main.java.game.nonsystem.Card.*;

public class SouthernPokerPlayer extends Player {
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(SouthernPokerPlayer.class.getName());

    public SouthernPokerPlayer() {
        super();
    }

    public SouthernPokerPlayer(String name) {
        super(name);
    }

    private void spacing() {
        for (int i = 0; i < 1; i++) System.out.println();
    }

    public void playATurn() {
        if(SouthernPokerGame.previousPlayer != null) System.out.println(SouthernPokerGame.previousPlayer.getName());
        spacing();
        if (!SouthernPokerGame.PokerField.isEmpty()) SouthernPokerGame.showPokerField();
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

        if (cardsToPlay != null) {
            SouthernPokerGame.PokerField.add(cardsToPlay);
            SouthernPokerGame.previousPlayer = this;
        }

        Action.clearScreen();
    }

    public void pass() {
        setState(Status.WAIT);
    }

    public ArrayList<Card> chooseCards() {
        System.out.println("Enter 0, 1, 2, 3, 4, ..., " + (hand.size() - 1) + " for card choosing: \n" +
                "(Several cards should be differ by space)\n" +
                "(Enter -1 if you want to pass.)\n" +
                "(Enter -2 if you want to view game rule.)");

        ArrayList<Card> cardChosen = new ArrayList<>();
        String str = scanner.nextLine();

        if (str.equals("-2")) {
            inspectRules();
            return chooseCards();
        }
        if (str.equals("-1")) {
            if(!SouthernPokerGame.previousPlayer.equals(this)) return null;
            else {
                System.out.println("You're free to play any card");
                return chooseCards();
            }
        }
        for (String s : str.trim().split("\\s+")) {
            if (s.isEmpty()) continue;
            cardChosen.add(hand.get(Integer.parseInt(s)));
        }
        return cardChosen;
    }




    private boolean valid(List<Card> playCards) {
        if(SouthernPokerGame.PokerField.isEmpty()) {
            return validCombo(playCards);
        }

        List<Card> fieldCards = SouthernPokerGame.PokerField.getFirst();
        if(playCards == null) return true;

        if (SouthernPokerGame.previousPlayer.equals(this)) { // Player continue to play since no one can play back
            int t = SouthernPokerGame.players.size();

            while (t-- > 0) {
                Player player = SouthernPokerGame.players.poll();
                if (player != null) {
                    player.setState(Status.READY);
                    SouthernPokerGame.players.add(player);
                }
                else break;
            }

            return validCombo(playCards);
        }


        return sameComboType(playCards, fieldCards) &&
                Card.isBigger(playCards, fieldCards);

    }

    private boolean validCombo(List<Card> playCards) {
        return playCards.size() == 1 ||
                doubleCombo(playCards) ||
                Card.tripleCombo(playCards) ||
                straight(playCards) ||
                quadCombo(playCards);
    }

    public void inspectRules(){

        while (true) {
            System.out.println("Type 1 if you want English game rule and 0 if you want Vietnamese game rule");
            int t = scanner.nextInt();
            scanner.nextLine();

            if (t == 0) {
                File file = new File("src/main/java/game/gamerule/Southern Poker/Rule - VietNamese.txt");
                System.out.println("Absolute path: " + file.getAbsolutePath());
                openTextFile(file);
                break;
            } else if (t == 1) {
                File file = new File("src/main/java/game/gamerule/Southern Poker/Rule - English.txt");
                openTextFile(file);
                break;
            } else {
                System.out.println("Please type as instructed!");
            }
        }
    }

    private void openTextFile(File file) {
        if(!file.exists()) {
            System.out.println("File not found!");
            return;
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("notepad.exe", file.getAbsolutePath());
            Process process = pb.start();
            process.waitFor(); // Pause until Notepad closes
        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Error opening file", e);
        }
    }

    public static boolean sameComboType(List<Card> cards1, List<Card> cards2) {
        return (cards1.size() == cards2.size()) &&
                ((doubleCombo(cards1) && doubleCombo(cards2)) ||
                        (tripleCombo(cards1) && tripleCombo(cards2)) ||
                        (quadCombo(cards1) && quadCombo(cards2)) ||
                        (straight(cards1) && straight(cards2)) ||
                        (cards1.size() == 1 && cards2.size() == 1));
    }

}