package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class NorthernCardGameController {

    @FXML private Label topPlayerName, leftPlayerName, rightPlayerName, bottomPlayerName;

    @FXML private ImageView topCard1, topCard2, topCard3;
    @FXML private ImageView bottomCard1, bottomCard2, bottomCard3;
    @FXML private ImageView leftCard1, leftCard2, leftCard3;
    @FXML private ImageView rightCard1, rightCard2, rightCard3;

    private String player1, player2, player3, player4;

    public void setPlayerNames(String p1, String p2, String p3, String p4) {
        this.player1 = p1;
        this.player2 = p2;
        this.player3 = p3;
        this.player4 = p4;
        topPlayerName.setText(p3);
        leftPlayerName.setText(p2);
        rightPlayerName.setText(p4);
        bottomPlayerName.setText(p1);
    }

    public void dealCardsToPlayers() {
        List<String> validCards = generateCardDeck();

        Collections.shuffle(validCards);

        List<String> cards1 = validCards.subList(0, 3);
        List<String> cards2 = validCards.subList(3, 6);
        List<String> cards3 = validCards.subList(6, 9);
        List<String> cards4 = validCards.subList(9, 12);

        setCardsToImage(bottomCard1, bottomCard2, bottomCard3, cards1);
        setCardsToImage(leftCard1, leftCard2, leftCard3, cards2);
        setCardsToImage(topCard1, topCard2, topCard3, cards3);
        setCardsToImage(rightCard1, rightCard2, rightCard3, cards4);
    }

    private List<String> generateCardDeck() {
        String[] suits = {"C", "D", "H", "S"};
        List<String> cards = new ArrayList<>();

        for (String suit : suits) {
            for (int i = 1; i <= 10; i++) {  // Không lấy J, Q, K (11-13)
                cards.add(suit + i); // Ví dụ: C1, H10, S5,...
            }
        }
        return cards;
    }

    private void setCardsToImage(ImageView c1, ImageView c2, ImageView c3, List<String> cardNames) {
        c1.setImage(loadCardImage(cardNames.get(0)));
        c2.setImage(loadCardImage(cardNames.get(1)));
        c3.setImage(loadCardImage(cardNames.get(2)));
    }

    private Image loadCardImage(String cardCode) {
        // cardCode ví dụ: "C1", "H10", "S5", "D9"
        String suitFolder = switch (cardCode.charAt(0)) {
            case 'C' -> "clubs";
            case 'D' -> "diamonds";
            case 'H' -> "hearts";
            case 'S' -> "spades";
            default -> throw new IllegalArgumentException("Invalid card suit: " + cardCode);
        };
        String fileName = cardCode + ".png";
        String path = "/image/card/" + suitFolder + "/" + fileName;
        var stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            System.err.println("Không tìm thấy ảnh: " + path);
            return null;
        }
        return new Image(stream);
    }
}