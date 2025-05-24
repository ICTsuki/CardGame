package client.controller;

import client.game.gamecore.NorthernPokerGame;
import client.game.gamecore.ThreeCards;
import client.game.nonsystem.NorthernPokerPlayer;
import client.game.nonsystem.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.IOException;


public class MenuController {
    private String playerName;

    @FXML
    private TextField playerNameField;

    public void TextNameEnter() {
        playerName = playerNameField.getText();
    }

    private void PopupNameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/view/PopupName.fxml"));
        VBox popupContent = loader.load();

        Stage popupStage = new Stage();
        popupStage.initOwner(null);
        popupStage.setTitle("Popup Window");
        popupStage.setScene(new Scene(popupContent, 200, 100));
        popupStage.show();
    }

    public void ThreeCardButtonClick(ActionEvent event) throws IOException {
        if(playerName == null || playerName.isEmpty()) {
            PopupNameWindow();
            return;
        }

        Player player = new Player(playerName);
        ThreeCards game = ThreeCards.getInstance();
        player.joinGame(game);

        // Use a named FXMLLoader so we can access the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/view/NorthernPoker.fxml"));
        Parent root = loader.load();

        // Now you can get the controller
        NorthernPokerController controller = loader.getController();
        controller.setPlayerName(playerName); // make sure you have this method in your controller

        // Switch scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void NorthernPokerButtonClick(ActionEvent event) throws IOException {
        if (playerName == null || playerName.isEmpty()) {
            PopupNameWindow();
            return;
        }

        NorthernPokerPlayer player = new NorthernPokerPlayer(playerName);
        NorthernPokerGame game = NorthernPokerGame.getInstance();
        player.joinGame(game);

        // Use a named FXMLLoader so we can access the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/view/NorthernPoker.fxml"));
        Parent root = loader.load();

        // Now you can get the controller
        NorthernPokerController controller = loader.getController();
        controller.setPlayerName(playerName); // make sure you have this method in your controller

        // Switch scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
