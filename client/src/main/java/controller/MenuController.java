package main.java.controller;

import main.java.game.gamecore.ThreeCards;
import main.java.game.nonsystem.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.Socket;


public class MenuController {
    private String playerName1, playerName2, playerName3, playerName4;
    private int playerCount = 0;

    @FXML
    private TextField playerNameField1, playerNameField2, playerNameField3, playerNameField4;

    public void TextNameField1Enter() {
        playerName1 = playerNameField1.getText();
        playerCount++;
    }
    public void TextNameField2Enter() {
        playerName2 = playerNameField2.getText();
        playerCount++;
    }
    public void TextNameField3Enter() {
        playerName3 = playerNameField3.getText();
        playerCount++;
    }
    public void TextNameField4Enter() {
        playerName4 = playerNameField4.getText();
        playerCount++;
    }


    private void PopupNameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resource/fxml/PopupName.fxml"));
        VBox popupContent = loader.load();

        Stage popupStage = new Stage();
        popupStage.initOwner(null);
        popupStage.setTitle("Popup Window");
        popupStage.setScene(new Scene(popupContent, 200, 100));
        popupStage.show();
    }

    public void ThreeCardButtonClick(ActionEvent event) throws IOException {
        if(playerCount < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need at least 2 players to start the game.");
            alert.setTitle("Game Error");
            alert.setHeaderText("Not Enough Players");
            alert.showAndWait();
            return;
        }

        Player player = new Player(playerName);
        ThreeCards game = ThreeCards.getInstance();
        player.joinGame(game);

        // Use a named FXMLLoader so we can access the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resource/fxml/NorthernPoker.fxml"));
        Parent root = loader.load();

        // Now you can get the controller
        NorthernPokerController controller = loader.getController();
        controller.setPlayerName(playerName); // make sure you have this method in your controller

        // Switch scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void NorthernPokerButtonClick(ActionEvent event) throws IOException {
        if(playerCount < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need at least 2 players to start the game.");
            alert.setTitle("Game Error");
            alert.setHeaderText("Not Enough Players");
            alert.showAndWait();
            return;
        }

        // Use a named FXMLLoader so we can access the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resource/fxml/NorthernPoker.fxml"));
        Parent root = loader.load();

        NorthernPokerController controller = loader.getController();
        if()


        // Switch scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
