package main.java.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import main.java.game.gamecore.NorthernPokerGame;
import main.java.game.nonsystem.Card;
import main.java.game.nonsystem.CardView;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.game.nonsystem.NorthernPokerPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PokerGameController implements Initializable {
    private final NorthernPokerGame game = NorthernPokerGame.getInstance();
    private NorthernPokerPlayer player1, player2, player3, player4;

    List<CardView> cardViews = new ArrayList<>();

    private final Map<Integer, VBox> seatMap = new HashMap<>();

    @FXML
    private Pane cardPane;
    @FXML
    private VBox topPlayerBox, leftPlayerBox, rightPlayerBox, bottomPlayerBox;

    private final List<ImageView> cardsBack = new ArrayList<>();
    private final List<ImageView> cardsFront = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seatMap.put(0, bottomPlayerBox);
        seatMap.put(1, leftPlayerBox);
        seatMap.put(2, topPlayerBox);
        seatMap.put(3, rightPlayerBox);

        List<Card> cards = NorthernPokerGame.getInstance().getDeck().getCards();
        Image backImage = new Image(getClass().getResourceAsStream(Card.BACK_IMAGE_PATH));

        for (Card card : cards) {
            ImageView front = new ImageView(new Image(getClass().getResourceAsStream(card.getFrontImagePath())));
            ImageView back = new ImageView(backImage);
            front.setFitWidth(80);
            front.setFitHeight(120);
            back.setFitWidth(80);
            back.setFitHeight(120); // Fixed typo: was "front.setFitHeight(120)"
            cardViews.add(new CardView(card, front, back));
            cardsBack.add(back);
        }

        double startX = 120;
        double startY = 34;
        for (ImageView card : cardsBack) {
            card.setLayoutX(startX);
            card.setLayoutY(startY);
            cardPane.getChildren().add(card);
        }

        startShuffle(() -> dealAnimation());
    }

    @FXML
    private void startShuffle(Runnable afterShuffle) {
        List<TranslateTransition> transitions = new ArrayList<>();
        for (ImageView card : cardsBack) {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), card);
            transition.setToX((Math.random() - 0.5) * 200); // Random x movement
            transition.setToY((Math.random() - 0.5) * 200);
            transition.setOnFinished(e -> resetCardPosition(card));
            transitions.add(transition);
        }

        for (TranslateTransition transition : transitions) {
            transition.play();
        }

        // After 1 sec delay, reset cards and run the next step
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    resetAllCards();

                    // Delay a little more before dealing
                    PauseTransition pause = new PauseTransition(Duration.seconds(1.0));
                    pause.setOnFinished(ev -> afterShuffle.run());
                    pause.play();
                });
            }
        }, 1000); // Wait for shuffle to finish
    }

    private void resetCardPosition(ImageView card) {
        card.setTranslateX(0);
        card.setTranslateY(0);
    }

    private void resetAllCards() {
        double startX = 120;
        double startY = 34;
        for (int i = 0; i < cardsBack.size(); i++) {
            ImageView card = cardsBack.get(i);
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), card);
            transition.setToX(startX - card.getLayoutX());
            transition.setToY(startY - card.getLayoutY());
            transition.play();
        }
    }

    public void dealAnimation() {
        // Initial stack position
        double sourceX = 120;
        double sourceY = 34;

        // === Deal to bottom player (you) ===
        double bottomStartX = 150; // Adjusted to center better
        double bottomY = 350;
        double spacing = 100; // Increased spacing to prevent overlap

        for (int i = 0; i < 12 && i < cardViews.size(); i++) {
            CardView cardView = cardViews.get(i);
            ImageView frontImage = cardView.getFront();

            frontImage.setLayoutX(sourceX);
            frontImage.setLayoutY(sourceY);
            frontImage.setFitWidth(80);
            frontImage.setFitHeight(120);
            cardPane.getChildren().add(frontImage);

            // Remove the corresponding back image for the bottom player's cards
            ImageView backImage = cardsBack.get(i);
            cardPane.getChildren().remove(backImage);

            double finalX = bottomStartX + i * spacing;
            double finalY = bottomY;

            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), frontImage);
            transition.setToX(finalX - sourceX);
            transition.setToY(finalY - sourceY);
            transition.setDelay(Duration.millis(i * 150));
            transition.setOnFinished(e -> {
                frontImage.setLayoutX(finalX);
                frontImage.setLayoutY(finalY);
                frontImage.setTranslateX(0);
                frontImage.setTranslateY(0);
            });

            transition.play();
        }


    }

    public void quitButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resource/fxml/Menu.fxml"));
        Parent root = loader.load();

        // Switch scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void placePlayer(int seatIndex, String playerName) {
        Image avatar = new Image(getClass().getResourceAsStream("/main/resource/image/player/playerimage.png"));
        ImageView imageView = new ImageView(avatar);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);

        Label label = new Label(playerName);
        VBox avatarBox = new VBox(imageView, label);
        avatarBox.setAlignment(Pos.CENTER);
        avatarBox.setSpacing(5);

        VBox seat = seatMap.get(seatIndex);
        Platform.runLater(() -> {
            seat.getChildren().clear(); // in case of reconnect/replacement
            seat.getChildren().add(avatarBox);
        });

        int playerCount = NorthernPokerGame.players.size();
        if (playerCount == 0) {
            player1 = new NorthernPokerPlayer(playerName);
            player1.joinGame(game);
        } else if (playerCount == 1) {
            player2 = new NorthernPokerPlayer(playerName);
            player2.joinGame(game);
        } else if (playerCount == 2) {
            player3 = new NorthernPokerPlayer(playerName);
            player3.joinGame(game);
        } else {
            player4 = new NorthernPokerPlayer(playerName);
            player4.joinGame(game);
        }
    }
}