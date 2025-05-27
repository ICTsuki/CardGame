package main.java.controller;

import main.java.util.AudioManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.RankEntry;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import java.io.IOException;
import java.util.*;

public class ThreeCardController {

    @FXML private VBox topPlayerBox, leftPlayerBox, rightPlayerBox, bottomPlayerBox;
    @FXML private VBox centerPane;
    @FXML private Button startGameButton, playAgainButton, quitButton;
    @FXML private VBox resultBox;
    @FXML private Label resultTitle;
    @FXML private VBox rankList;
    @FXML private ToggleButton musicToggleButton;

    private final Map<Integer, VBox> seatMap = new LinkedHashMap<>();
    private final Map<Integer, String> playerNames = new HashMap<>();


    @FXML
    public void initialize() {
        musicToggleButton.setSelected(AudioManager.getInstance().isPlaying());
        updateButtonText();
        playAgainButton.setVisible(false);
        resultBox.setVisible(false);

        seatMap.put(0, bottomPlayerBox);
        seatMap.put(1, leftPlayerBox);
        seatMap.put(2, topPlayerBox);
        seatMap.put(3, rightPlayerBox);

        for (Map.Entry<Integer, String> entry : playerNames.entrySet()) {
            placePlayer(entry.getKey(), entry.getValue());
        }


    }

    public void setPlayerName1(String name) { playerNames.put(0, name); }
    public void setPlayerName2(String name) { playerNames.put(1, name); }
    public void setPlayerName3(String name) { playerNames.put(2, name); }
    public void setPlayerName4(String name) { playerNames.put(3, name); }

    public void placePlayer(int seatIndex, String playerName) {
        VBox seatBox = getSeatBox(seatIndex);
        seatMap.put(seatIndex, seatBox);

        Image avatar = new Image(getClass().getResourceAsStream("/image/player/playerimage.png"));
        ImageView imageView = new ImageView(avatar);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);

        Label label = new Label(playerName);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox avatarBox = new VBox(imageView, label);
        avatarBox.setAlignment(Pos.CENTER);
        avatarBox.setSpacing(5);

        VBox playerBlock = new VBox(10);
        playerBlock.setAlignment(Pos.CENTER);
        playerBlock.getChildren().add(avatarBox);

        seatBox.getChildren().clear();
        seatBox.getChildren().add(playerBlock);
    }

    private VBox getSeatBox(int seatIndex) {
        return switch (seatIndex) {
            case 0 -> bottomPlayerBox;
            case 1 -> leftPlayerBox;
            case 2 -> topPlayerBox;
            case 3 -> rightPlayerBox;
            default -> throw new IllegalArgumentException("Invalid seat index");
        };
    }

    @FXML
    public void startGameButtonClick() {
        startGameButton.setVisible(false);
        playAgainButton.setVisible(true);
        dealCardsToPlayers();
    }

    @FXML
    public void playAgainButtonClick() {
        clearAllCards();
        resultBox.setVisible(false);
        dealCardsToPlayers();
    }

    @FXML
    public void quitButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SequentialTransition setCardsToWithAnimation(VBox seatBox, List<String> cardCodes) {
        HBox cards = new HBox(15);
        cards.setAlignment(Pos.CENTER);
        SequentialTransition sequence = new SequentialTransition();

        for (String code : cardCodes) {
            ImageView cardView = new ImageView();
            cardView.setFitWidth(70);
            cardView.setFitHeight(110);
            cards.getChildren().add(cardView);

            Image cardImage = loadCardImage(code);
            PauseTransition delay = new PauseTransition(Duration.millis(300));
            delay.setOnFinished(e -> cardView.setImage(cardImage));
            sequence.getChildren().add(delay);
        }

        VBox playerBlock = (VBox) seatBox.getChildren().get(0);
        playerBlock.getChildren().removeIf(n -> n instanceof HBox);
        playerBlock.getChildren().add(cards);
        return sequence;
    }

    private void dealCardsToPlayers() {
        List<String> deck = generateCardDeck();
        Collections.shuffle(deck);

        int index = 0;
        Map<String, Integer> scores = new HashMap<>();
        Map<String, List<String>> playerCards = new HashMap<>();
        List<SequentialTransition> animations = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : playerNames.entrySet()) {
            int seatIndex = entry.getKey();
            String name = entry.getValue();
            VBox seatBox = seatMap.get(seatIndex);

            List<String> cards = deck.subList(index, index + 3);
            index += 3;
            playerCards.put(name, new ArrayList<>(cards));

            SequentialTransition seq = setCardsToWithAnimation(seatBox, cards);
            animations.add(seq);

            int score = cards.stream().mapToInt(this::cardValue).sum() % 10;
            scores.put(name, score);
        }

        SequentialTransition allAnimations = new SequentialTransition();
        allAnimations.getChildren().addAll(animations);
        allAnimations.setOnFinished(e -> Platform.runLater(() -> showRankPopup(scores, playerCards)));
        allAnimations.play();
    }

    private void clearAllCards() {
        for (VBox seatBox : seatMap.values()) {
            if (!seatBox.getChildren().isEmpty()) {
                Node playerBlock = seatBox.getChildren().get(0);
                if (playerBlock instanceof VBox vbox) {
                    vbox.getChildren().removeIf(n -> n instanceof HBox);
                }
            }
        }
    }

    private List<String> generateCardDeck() {
        String[] suits = {"C", "D", "H", "S"};
        List<String> cards = new ArrayList<>();
        for (String suit : suits) {
            for (int i = 3; i <= 10; i++) {
                cards.add(suit + i);
            }
            cards.add(suit + "14");
            cards.add(suit + "15");
        }

        return cards;
    }

    private Image loadCardImage(String cardCode) {
        String suitFolder = switch (cardCode.charAt(0)) {
            case 'C' -> "clubs";
            case 'D' -> "diamonds";
            case 'H' -> "hearts";
            case 'S' -> "spades";
            default -> throw new IllegalArgumentException("Invalid suit: " + cardCode);
        };
        String path = "/image/card/" + suitFolder + "/" + cardCode + ".png";
        var stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            System.err.println("Không tìm thấy ảnh: " + path);
            return null;
        }
        return new Image(stream);
    }

    private void showRankPopup(Map<String, Integer> scores, Map<String, List<String>> playerCards) {
        List<RankEntry> ranks = new ArrayList<>();

        for (String name : scores.keySet()) {
            ranks.add(new RankEntry(name, scores.get(name)));
        }

        ranks.sort((a, b) -> {
            int cmp = Integer.compare(b.getScore(), a.getScore());
            if (cmp != 0) return cmp;

            List<String> cardsA = playerCards.get(a.getPlayerName());
            List<String> cardsB = playerCards.get(b.getPlayerName());

            // Tìm lá có số lớn nhất
            String maxA = cardsA.stream().max(Comparator.comparingInt(this::cardValue)).orElse("C1");
            String maxB = cardsB.stream().max(Comparator.comparingInt(this::cardValue)).orElse("C1");

            int valueA = cardValue(maxA);
            int valueB = cardValue(maxB);

            if (valueA != valueB) {
                return Integer.compare(valueB, valueA);  // ai lớn hơn thì thắng
            }

            // Nếu cùng số → so chất
            return Integer.compare(suitRank(maxB), suitRank(maxA));
        });
        resultTitle.setText("🏆 Bảng Xếp Hạng Người Chơi");
        rankList.getChildren().clear();

        int rank = 1;
        for (RankEntry re : ranks) {
            Label label = new Label(rank++ + ". " + re.getPlayerName() + " - " + re.getScore() + " điểm");
            label.setStyle("-fx-font-size: 14px;");
            rankList.getChildren().add(label);
        }

        resultBox.setVisible(true);
    }

    private int cardValue(String cardCode) {
        int raw = Integer.parseInt(cardCode.substring(1));
        return switch(raw) {
            case 14 -> 1;
            case 15 -> 2;
            default -> raw;
        };
    }

    private int suitRank(String cardCode) {
        char suit = cardCode.charAt(0);
        return switch (suit) {
            case 'S' -> 4;
            case 'H' -> 3;
            case 'D' -> 2;
            case 'C' -> 1;
            default -> 0;
        };
    }

    @FXML
    public void closeResultBox() {
        resultBox.setVisible(false);
    }

    @FXML
    public void toggleMusic() {
        AudioManager.getInstance().toggle();
        updateButtonText();
    }

    private void updateButtonText() {
        musicToggleButton.setText(AudioManager.getInstance().isPlaying() ? "Pause Music" : "Play Music");
    }
}