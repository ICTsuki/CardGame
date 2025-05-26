package main.java.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.RankEntry;

import java.io.IOException;
import java.util.*;

public class NorthernPokerController {

    @FXML private VBox topPlayerBox, leftPlayerBox, rightPlayerBox, bottomPlayerBox;
    @FXML private VBox centerPane;
    @FXML private Button startGameButton, playAgainButton, quitButton;

    private final List<ImageView> topCards = new ArrayList<>();
    private final List<ImageView> leftCards = new ArrayList<>();
    private final List<ImageView> rightCards = new ArrayList<>();
    private final List<ImageView> bottomCards = new ArrayList<>();

    private final Map<Integer, VBox> seatMap = new LinkedHashMap<>();  // preserve order
    private final Map<Integer, String> playerNames = new HashMap<>();

    @FXML
    public void initialize() {
        // Không thêm ghế sẵn — sẽ chỉ thêm khi có người chơi thực sự
    }

    public void setPlayerName1(String name) {
        playerNames.put(0, name);
    }
    public void setPlayerName2(String name) {
        playerNames.put(1, name);
    }
    public void setPlayerName3(String name) {
        playerNames.put(2, name);
    }
    public void setPlayerName4(String name) {
        playerNames.put(3, name);
    }

    public void placePlayer(int seatIndex, String playerName) {
        VBox seatBox = getSeatBox(seatIndex);
        seatMap.put(seatIndex, seatBox);  // chỉ lưu những người thật sự chơi

        Image avatar = new Image(getClass().getResourceAsStream("/image/player/playerimage.png"));
        ImageView imageView = new ImageView(avatar);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);

        Label label = new Label(playerName);
        VBox avatarBox = new VBox(imageView, label);
        avatarBox.setAlignment(Pos.CENTER);
        avatarBox.setSpacing(5);

        seatBox.getChildren().clear();
        seatBox.getChildren().add(avatarBox);
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
        dealCardsToPlayers();
    }

    @FXML
    public void playAgainButtonClick() {
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

    private void dealCardsToPlayers() {
        List<String> deck = generateCardDeck();
        Collections.shuffle(deck);

        int index = 0;
        Map<String, Integer> scores = new HashMap<>();

        for (Map.Entry<Integer, VBox> entry : seatMap.entrySet()) {
            int seatIndex = entry.getKey();
            VBox seatBox = entry.getValue();
            String name = playerNames.get(seatIndex);

            List<String> cards = deck.subList(index, index + 3);
            index += 3;

            setCardsTo(seatBox, cards);

            int score = cards.stream()
                    .mapToInt(c -> Integer.parseInt(c.substring(1)))
                    .sum() % 10;
            scores.put(name, score);
        }

        showRankPopup(scores);
    }

    private void setCardsTo(VBox seatBox, List<String> cardCodes) {
        HBox cards = new HBox(10);
        for (String code : cardCodes) {
            ImageView img = new ImageView(loadCardImage(code));
            img.setFitWidth(60);
            img.setFitHeight(90);
            cards.getChildren().add(img);
        }

        Label nameLabel = null;
        ImageView avatar = null;

        for (Node node : seatBox.getChildren()) {
            if (node instanceof VBox vbox) {
                for (Node child : vbox.getChildren()) {
                    if (child instanceof ImageView img) avatar = img;
                    if (child instanceof Label lbl) nameLabel = lbl;
                }
            }
        }

        seatBox.getChildren().clear();
        seatBox.getChildren().add(cards);
        if (avatar != null || nameLabel != null) {
            VBox playerInfo = new VBox();
            if (avatar != null) playerInfo.getChildren().add(avatar);
            if (nameLabel != null) playerInfo.getChildren().add(nameLabel);
            playerInfo.setAlignment(Pos.CENTER);
            playerInfo.setSpacing(5);
            seatBox.getChildren().add(playerInfo);
        }
    }

    private List<String> generateCardDeck() {
        String[] suits = {"C", "D", "H", "S"};
        List<String> cards = new ArrayList<>();
        for (String suit : suits) {
            for (int i = 1; i <= 10; i++) {
                cards.add(suit + i);
            }
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
        String fileName = cardCode + ".png";
        String path = "/image/card/" + suitFolder + "/" + fileName;
        var stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            System.err.println("Không tìm thấy ảnh: " + path);
            return null;
        }
        return new Image(stream);
    }

    private void showRankPopup(Map<String, Integer> scores) {
        List<RankEntry> ranks = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            ranks.add(new RankEntry(entry.getKey(), entry.getValue()));
        }
        ranks.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        StringBuilder result = new StringBuilder("🏆 Bảng Xếp Hạng:\n");
        int rank = 1;
        for (RankEntry re : ranks) {
            result.append(rank++).append(". ")
                    .append(re.getPlayerName()).append(" - ")
                    .append(re.getScore()).append(" điểm\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kết quả ván bài");
        alert.setHeaderText("Bảng xếp hạng người chơi");
        alert.setContentText(result.toString());
        alert.showAndWait();
    }
}