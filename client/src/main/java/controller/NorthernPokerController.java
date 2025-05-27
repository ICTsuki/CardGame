
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

public class NorthernPokerController {
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

        finishedPlayers.clear();
        lastPlayedCards.clear();
        startGameButton.setVisible(false);
        playAgainButton.setVisible(true);
        playButton.setVisible(true);
        passButton.setVisible(true);
        centerCardBox.getChildren().clear();
    }

    @FXML
    public void playAgainButtonClick() {
        playerCards.clear();
        selectedCards.clear();
        centerCardBox.getChildren().clear();
        finishedPlayers.clear();
        playerOrder.clear();
        lastPlayedCards.clear();
        startGameButtonClick();
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
                playerOrder.clear();
                playerOrder.addAll(playerNames.keySet());
                while (playerOrder.get(0) != entry.getKey()) {
                    Collections.rotate(playerOrder, -1);
                }
                break;
            }
        }
    }

    private void rotatePlayersToFirst() {
        // đã xoay trong determineFirstPlayer
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
        if (playerCards.get(current).isEmpty()) return;

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

    private boolean isValidMove(List<String> playedCards, List<String> lastCards) {
        if (lastCards.isEmpty()) return true;
        if (playedCards.size() != lastCards.size()) return false;

        List<Integer> played = playedCards.stream().map(this::cardSortValue).sorted().toList();
        List<Integer> last = lastCards.stream().map(this::cardSortValue).sorted().toList();
        return played.get(played.size() - 1) > last.get(last.size() - 1);
    }

    @FXML
    public void playButtonClick() {
        if (selectedCards.isEmpty()) return;

        int current = playerOrder.get(0);
        List<String> cards = playerCards.get(current);
        List<String> playedCards = new ArrayList<>(selectedCards);

        if (!isValidMove(playedCards, lastPlayedCards)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Không hợp lệ");
            alert.setHeaderText(null);
            alert.setContentText("Không thể đánh bài này.");
            alert.showAndWait();
            return;
        }

        cards.removeAll(playedCards);
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

        lastPlayedCards = new ArrayList<>(playedCards);
        selectedCards.clear();

        if (cards.isEmpty()) {
            finishedPlayers.add(current);
            playerOrder.remove(0);
        } else {
            Collections.rotate(playerOrder, -1);
        }

        checkGameOver();
        renderPlayers();
        displayCurrentPlayerCards();
    }

    @FXML
    public void passButtonClick() {
        Collections.rotate(playerOrder, -1);
        renderPlayers();
        displayCurrentPlayerCards();
    }

    private void checkGameOver() {
        if (playerOrder.size() == 1) {
            finishedPlayers.add(playerOrder.get(0));
            StringBuilder result = new StringBuilder("Kết thúc trò chơi:\n");
            for (int i = 0; i < finishedPlayers.size(); i++) {
                String name = playerNames.get(finishedPlayers.get(i));
                result.append("Hạng ").append(i + 1).append(": ").append(name).append("\n");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Trò chơi kết thúc");
            alert.setHeaderText("Xếp hạng:");
            alert.setContentText(result.toString());
            alert.showAndWait();

            playButton.setDisable(true);
            passButton.setDisable(true);
        }
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