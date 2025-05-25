package controller;

import game.gamecore.NorthernPokerGame;
import game.nonsystem.Card;
import game.nonsystem.CardView;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PokerGameController implements Initializable {

    List<CardView> cardViews = new ArrayList<>();

    @FXML
    private Label playerName;

    @FXML
    private Pane cardPane;

    private final List<ImageView> cardsBack = new ArrayList<>();
    private final List<ImageView> cardsFront = new ArrayList<>();

    public void setPlayerName(String name) {
        playerName.setText(name);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Card> cards = NorthernPokerGame.getInstance().getDeck().getCards();
        Image backImage = new Image(getClass().getResourceAsStream(Card.BACK_IMAGE_PATH));

        for(Card card : cards) {
            ImageView front = new ImageView(new Image(getClass().getResourceAsStream(card.getFrontImagePath())));
            ImageView back = new ImageView(backImage);
            front.setFitWidth(80);    front.setFitHeight(120);
            back.setFitWidth(80);    front.setFitHeight(120);
            cardViews.add(new CardView(card, front, back));
            cardsBack.add(back);
        }

        double startX = 120;
        double startY = 34;
        for (ImageView card : cardsBack) {
            card.setLayoutX(startX);
            card.setLayoutY(startY);
            cardPane.getChildren().add(card);
            startY += 2; // Slight offset for stack effect
        }

        startShuffle();
    }

    @FXML
    private void startShuffle() {
        // Create shuffle animation
        List<TranslateTransition> transitions = new ArrayList<>();
        for (ImageView card : cardsBack) {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), card);
            transition.setToX((Math.random() - 0.5) * 200); // Random x movement (-100 to 100)
            transition.setToY((Math.random() - 0.5) * 200); // Random y movement (-100 to 100)
            transition.setOnFinished(e -> resetCardPosition(card));
            transitions.add(transition);
        }

        // Play all transitions
        for (TranslateTransition transition : transitions) {
            transition.play();
        }

        // Reset to stack after 1 second
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> resetAllCards());
            }
        }, 1000);
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
            transition.setToY(startY - card.getLayoutY() + i * 2); // Stack them back
            transition.play();
        }
    }

    public void quitButtonClick(ActionEvent event) throws IOException {
        // Use a named FXMLLoader so we can access the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
        Parent root = loader.load();

        // Switch scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
    }


}
