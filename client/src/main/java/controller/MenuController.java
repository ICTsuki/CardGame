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


    public void ThreeCardButtonClick(ActionEvent event) throws IOException {
        if(playerCount < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need at least 2 players to start the game.");
            alert.setTitle("Game Error");
            alert.setHeaderText("Not Enough Players");
            alert.showAndWait();
            return;
        }

        Player player = new Player(playerName1);
        ThreeCards game = ThreeCards.getInstance();
        player.joinGame(game);

        // Use a named FXMLLoader so we can access the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resource/fxml/NorthernPoker.fxml"));
        Parent root = loader.load();

        // Now you can get the controller
        NorthernPokerController controller = loader.getController();
        if(playerName1 != null && !playerName1.isEmpty()) {
            controller.placePlayer(0, playerName1);
        }
        if(playerName2 != null && !playerName2.isEmpty()) {
            controller.placePlayer(1, playerName2);
        }
        if(playerName3 != null && !playerName3.isEmpty()) {
            controller.placePlayer(2, playerName3);
        }
        if(playerName4 != null && !playerName4.isEmpty()) {
            controller.placePlayer(3, playerName4);
        }

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
        if(playerName1 != null && !playerName1.isEmpty()) {
            controller.placePlayer(0, playerName1);
        }
        if(playerName2 != null && !playerName2.isEmpty()) {
            controller.placePlayer(1, playerName2);
        }
        if(playerName3 != null && !playerName3.isEmpty()) {
            controller.placePlayer(2, playerName3);
        }
        if(playerName4 != null && !playerName4.isEmpty()) {
            controller.placePlayer(3, playerName4);
        }



        // Switch scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
