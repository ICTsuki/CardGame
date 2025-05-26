package model.nonsystem;

import model.gamecore.NorthernPokerGame;
import model.gameenum.Status;
import model.gameinterface.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import java.util.logging.Logger;
import java.util.logging.Level;

public class NorthenPokerPlayer extends Player {
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(NorthenPokerPlayer.class.getName());

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
                "(Enter -1 if you want to pass.)\n" +
                "(Enter -2 if you want to view game rule.)");

        ArrayList<Card> cardChosen = new ArrayList<>();
        String str = scanner.nextLine();

        if (str.equals("-2")) {
            inspectRules();
            return chooseCards();
        }
        if (str.equals("-1")) return null;
        for (String s : str.trim().split("\\s+")) {
            if (s.isEmpty()) continue;
            cardChosen.add(hand.get(Integer.parseInt(s)));
        }


        return cardChosen;
    }




    private boolean valid(List<Card> playCards) {
        if(NorthernPokerGame.PokerField.isEmpty()) {
            return validCombo(playCards);
        }

        List<Card> fieldCards = NorthernPokerGame.PokerField.getFirst();
        if(playCards == null) return true;

        if (NorthernPokerGame.previousPlayer.equals(this)) { // Player continue to play since no one can play back
            int t = NorthernPokerGame.players.size();

            while (t-- > 0) {
                Player player = NorthernPokerGame.players.poll();
                if (player != null) {
                    player.setState(Status.READY);
                    NorthernPokerGame.players.add(player);
                }
                else break;
            }

            return validCombo(playCards);
        }

        return Card.sameSuits(Arrays.asList(playCards, fieldCards)) &&
                Card.isBigger(playCards, fieldCards);
    }

    private boolean validCombo(List<Card> playCards) {
        return playCards.size() == 1 ||
                Card.doubleCombo(playCards) ||
                Card.tripleCombo(playCards) ||
                Card.straight(playCards) ||
                Card.quadCombo(playCards);
    }

    public void inspectRules(){

        while (true) {
            System.out.println("Type 1 if you want English game rule and 0 if you want Vietnamese game rule");
            int t = scanner.nextInt();
            scanner.nextLine();

            if (t == 0) {
                File file = new File("src/model/gamerule/Northern Poker/Rule - VietNamese.txt");
                System.out.println("Absolute path: " + file.getAbsolutePath());
                openTextFile(file);
                break;
            } else if (t == 1) {
                File file = new File("src/model/gamerule/Northern Poker/Rule - English.txt");
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


}