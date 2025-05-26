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
import main.java.Server;
import main.java.game.nonsystem.Player;

import java.io.IOException;
import java.util.*;

public class NorthernPokerController {

    @FXML
    private VBox topPlayerBox, leftPlayerBox, rightPlayerBox, bottomPlayerBox;


    @FXML
    private Label playerName;

    @FXML
    public void initialize() {
        seatMap.put(0, bottomPlayerBox);
        seatMap.put(1, leftPlayerBox);
        seatMap.put(2, topPlayerBox);
        seatMap.put(3, rightPlayerBox);
    }

    private final Map<Integer, VBox> seatMap = new HashMap<>();

    public void setPlayerName(String name) {
        playerName.setText(name);
    }

    public void startGameButtonClick(ActionEvent event) throws IOException {
        Server server = Server.getInstance();
        if(server.getServerCount() < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need at least 2 players to start the game.");
            alert.setTitle("Game Error");
            alert.setHeaderText("Not Enough Players");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resource/fxml/StartedNorthernPoker.fxml"));
        Parent root = loader.load();

        PokerGameController controller = loader.getController();
        controller.setPlayerName(playerName.getText());

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

    public void placePlayer(int seatIndex, String name) {
        Image avatar = new Image(getClass().getResourceAsStream("/main/resource/image/player/playerimage.png"));
        ImageView imageView = new ImageView(avatar);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);

        Label label = new Label(name);

        VBox avatarBox = new VBox(imageView, label);
        avatarBox.setAlignment(Pos.CENTER);
        avatarBox.setSpacing(5);

        VBox seat = seatMap.get(seatIndex);
        Platform.runLater(() -> {
            seat.getChildren().clear();
            seat.getChildren().add(avatarBox);
        });
    }
}
