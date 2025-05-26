package main.java.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class NorthernPokerController {

    @FXML
    private VBox topPlayerBox, leftPlayerBox, rightPlayerBox, bottomPlayerBox;

    private String playerName1, playerName2, playerName3, playerName4;

    @FXML
    public void initialize() {
        seatMap.put(0, bottomPlayerBox);
        seatMap.put(1, leftPlayerBox);
        seatMap.put(2, topPlayerBox);
        seatMap.put(3, rightPlayerBox);
    }

    private final Map<Integer, VBox> seatMap = new HashMap<>();

    public void startGameButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resource/fxml/StartedNorthernPoker.fxml"));
        Parent root = loader.load();

        PokerGameController controller = loader.getController();
        if(playerName1 != null && !playerName1.isEmpty()) {
            placePlayer(0, playerName1);
        }
        if(playerName2 != null && !playerName2.isEmpty()) {
            placePlayer(1, playerName2);
        }
        if(playerName3 != null && !playerName3.isEmpty()) {
            placePlayer(2, playerName3);
        }
        if(playerName4 != null && !playerName4.isEmpty()) {
            placePlayer(3, playerName4);
        }

        // Switch scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void quitButtonClick(ActionEvent event) throws IOException {
        // Use a named FXMLLoader so we can access the controller
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
    }

}
