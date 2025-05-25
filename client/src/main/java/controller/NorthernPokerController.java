package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class NorthernPokerController {

    @FXML
    private Label playerName;

    public void setPlayerName(String name) {
        playerName.setText(name);
    }

    public void startGameButtonClick(ActionEvent event) throws IOException {
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
}
