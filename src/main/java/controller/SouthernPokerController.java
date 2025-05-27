package main.java.controller;

import main.java.util.AudioManager;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class SouthernPokerController {
    @FXML private VBox topPlayerBox, leftPlayerBox, rightPlayerBox, bottomPlayerBox, centerPane;
    @FXML private Button startGameButton, quitButton, playAgainButton, playButton, passButton;
    @FXML private ToggleButton musicToggleButton;
    @FXML private HBox centerCardBox;

    private final Map<Integer, VBox> seatMap = new LinkedHashMap<>();
    private final Map<Integer, String> playerNames = new HashMap<>();
    private final Map<Integer, List<String>> playerCards = new HashMap<>();
    private final Map<String, ImageView> cardImageViews = new HashMap<>();
    private final Set<String> selectedCards = new HashSet<>();
    private final List<Integer> playerOrder = new ArrayList<>();
    private final List<Integer> finishedPlayers = new ArrayList<>();
    private List<String> lastPlayedCards = new ArrayList<>();
    private int firstPlayer = -1;
    private int lastPlayerIndex = -1;
    private boolean firstTurn = true;

    public void setPlayerName1(String name) { playerNames.put(0, name); }
    public void setPlayerName2(String name) { playerNames.put(1, name); }
    public void setPlayerName3(String name) { playerNames.put(2, name); }
    public void setPlayerName4(String name) { playerNames.put(3, name); }

    @FXML
    public void initialize() {
        seatMap.put(0, bottomPlayerBox);
        seatMap.put(1, leftPlayerBox);
        seatMap.put(2, topPlayerBox);
        seatMap.put(3, rightPlayerBox);

        playAgainButton.setVisible(false);
        playButton.setVisible(false);
        passButton.setVisible(false);
        centerCardBox.setAlignment(Pos.CENTER);
        musicToggleButton.setSelected(AudioManager.getInstance().isPlaying());
        updateMusicButtonText();
    }

    @FXML
    public void toggleMusic() {
        AudioManager.getInstance().toggle();
        updateMusicButtonText();
    }

    private void updateMusicButtonText() {
        musicToggleButton.setText(AudioManager.getInstance().isPlaying() ? "Pause Music" : "Play Music");
    }

    @FXML
    public void startGameButtonClick() {
        dealCards();
        determineFirstPlayer();
        rotatePlayersToFirst();
        renderPlayers();
        displayCurrentPlayerCards();
        startGameButton.setVisible(false);
        playAgainButton.setVisible(true);
        playButton.setVisible(true);
        passButton.setVisible(true);
        firstTurn = true;
    }

    @FXML
    public void playAgainButtonClick() {
        playerCards.clear();
        selectedCards.clear();
        centerCardBox.getChildren().clear();
        finishedPlayers.clear();
        playerOrder.clear();
        lastPlayedCards.clear();
        lastPlayerIndex = -1;
        startGameButtonClick();
        firstTurn = true;
    }

    private void dealCards() {
        List<String> deck = generateCardDeck();
        Collections.shuffle(deck);
        int i = 0;
        for (Integer seat : playerNames.keySet()) {
            List<String> cards = new ArrayList<>(deck.subList(i, i + 13));
            cards.sort(Comparator.comparingInt(this::cardSortValue));
            playerCards.put(seat, cards);
            i += 13;
        }
    }

    private void determineFirstPlayer() {
        for (Map.Entry<Integer, List<String>> entry : playerCards.entrySet()) {
            if (entry.getValue().contains("S3")) {
                firstPlayer = entry.getKey();
                break;
            }
        }
        if (firstPlayer == -1) {
            firstPlayer = playerNames.keySet().iterator().next();
        }
    }

    private void rotatePlayersToFirst() {
        playerOrder.clear();
        playerOrder.addAll(playerNames.keySet());
        while (playerOrder.get(0) != firstPlayer) {
            Collections.rotate(playerOrder, -1);
        }
    }

    private void renderPlayers() {
        List<VBox> seats = List.of(bottomPlayerBox, leftPlayerBox, topPlayerBox, rightPlayerBox);
        for (int i = 0; i < playerOrder.size(); i++) {
            int playerId = playerOrder.get(i);
            String name = playerNames.get(playerId);
            placePlayer(i, name);
            seatMap.put(playerId, seats.get(i));
        }
    }

    private void displayCurrentPlayerCards() {
        clearAllCards();
        int current = playerOrder.get(0);
        VBox seatBox = seatMap.get(current);
        HBox cardRow = new HBox(10);
        cardRow.setAlignment(Pos.CENTER);
        cardImageViews.clear();
        selectedCards.clear();
        for (String code : playerCards.get(current)) {
            Image img = loadCardImage(code);
            if (img != null) {
                ImageView imageView = new ImageView(img);
                imageView.setFitWidth(60);
                imageView.setFitHeight(90);
                imageView.setUserData(code);
                imageView.setOnMouseClicked(e -> toggleCardSelection(imageView));
                cardImageViews.put(code, imageView);
                cardRow.getChildren().add(imageView);
            }
        }
        seatBox.getChildren().add(cardRow);
    }

    private void toggleCardSelection(ImageView imageView) {
        String code = (String) imageView.getUserData();
        if (selectedCards.contains(code)) {
            imageView.setTranslateY(0);
            selectedCards.remove(code);
        } else {
            imageView.setTranslateY(-20);
            selectedCards.add(code);
        }
    }

    private void clearAllCards() {
        for (VBox box : seatMap.values()) {
            box.getChildren().removeIf(n -> n instanceof HBox);
        }
    }

    private List<String> generateCardDeck() {
        String[] suits = {"C", "D", "H", "S"};
        List<String> deck = new ArrayList<>();
        for (int value = 3; value <= 15; value++) {
            for (String suit : suits) {
                deck.add(suit + value);
            }
        }
        return deck;
    }

    private int cardSortValue(String card) {
        return Integer.parseInt(card.substring(1));
    }

    private Image loadCardImage(String code) {
        char suitChar = code.charAt(0);
        String suit = switch (suitChar) {
            case 'C' -> "clubs";
            case 'D' -> "diamonds";
            case 'H' -> "hearts";
            case 'S' -> "spades";
            default -> "unknown";
        };
        String value = code.substring(1);
        String path = "/image/card/" + suit + "/" + suitChar + value + ".png";
        var stream = getClass().getResourceAsStream(path);
        return stream != null ? new Image(stream) : null;
    }

    @FXML
    public void playButtonClick() {
        if (selectedCards.isEmpty()) return;

        int current = playerOrder.get(0);
        List<String> cards = playerCards.get(current);
        List<String> playedCards = new ArrayList<>(selectedCards);

        if (lastPlayedCards.isEmpty()) {

            if (cards.contains("S3") && !playedCards.contains("S3")) {
                showAlert("Player with 3♠ must play 3♠ in the first play.");
                return;
            }

            String type = detectType(playedCards);
            if (type.equals("unknown")) {
                showAlert("Cannot play these card(s).");
                return;
            }
        } else {
            if (!isValidPlay(playedCards)) {
                showAlert("Cannot play these card(s)");
                return;
            }
        }

        cards.removeAll(playedCards);
        lastPlayedCards = new ArrayList<>(playedCards);
        lastPlayerIndex = current;
        firstTurn = false;

        centerCardBox.getChildren().clear();
        for (String code : playedCards) {
            Image img = loadCardImage(code);
            if (img != null) {
                ImageView iv = new ImageView(img);
                iv.setFitWidth(60);
                iv.setFitHeight(90);
                TranslateTransition tt = new TranslateTransition(Duration.millis(300), iv);
                tt.setFromY(-40);
                tt.setToY(0);
                tt.play();
                centerCardBox.getChildren().add(iv);
            }
        }

        selectedCards.clear();
        if (cards.isEmpty() && !finishedPlayers.contains(current)) {
            finishedPlayers.add(current);
            if (finishedPlayers.size() == 1) {
                showRanking();
                return;
            }
        }

        nextTurn();
    }

    private boolean isValidPlay(List<String> played) {
        if (lastPlayedCards.isEmpty() || lastPlayerIndex == playerOrder.get(0)) return true;
        if (played.size() != lastPlayedCards.size()) return false;
        String type = detectType(played);
        String lastType = detectType(lastPlayedCards);
        return type.equals(lastType) && compare(played, lastPlayedCards) > 0;
    }

    private String detectType(List<String> cards) {
        int size = cards.size();
        Set<Integer> values = new HashSet<>();
        for (String c : cards) values.add(cardSortValue(c));
        if (size == 1) return "single";
        if (size == 2 && values.size() == 1) return "pair";
        if (size == 3 && values.size() == 1) return "triple";
        if (size == 4 && values.size() == 1) return "four";
        return "unknown";
    }

    private int compare(List<String> a, List<String> b) {
        List<Integer> va = a.stream().map(this::cardSortValue).sorted().toList();
        List<Integer> vb = b.stream().map(this::cardSortValue).sorted().toList();
        return va.getLast() - vb.getLast();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showRanking() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < finishedPlayers.size(); i++) {
            int id = finishedPlayers.get(i);
            sb.append("Winner ").append(": ").append(playerNames.get(id)).append("\n");
        }
        showAlert(sb.toString());
        playButton.setVisible(false);
        passButton.setVisible(false);
    }

    @FXML
    public void passButtonClick() {
        nextTurn();
    }

    private void nextTurn() {
        Collections.rotate(playerOrder, -1);
        renderPlayers();
        displayCurrentPlayerCards();
    }

    @FXML
    public void quitButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void placePlayer(int seatIndex, String playerName) {
        VBox seatBox = switch (seatIndex) {
            case 0 -> bottomPlayerBox;
            case 1 -> leftPlayerBox;
            case 2 -> topPlayerBox;
            case 3 -> rightPlayerBox;
            default -> throw new IllegalArgumentException("Invalid seat index");
        };
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
}