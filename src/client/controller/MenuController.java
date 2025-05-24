package client.controller;

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

    public void ThreeCardButtonClick(ActionEvent event) throws IOException {
        Parent ThreeCardParent = FXMLLoader.load(getClass().getResource("/client/view/ThreeCard.fxml"));
        Scene ThreeCardScene = new Scene(ThreeCardParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ThreeCardScene);
    }

    public void NorthernPokerButtonClick(ActionEvent event) throws IOException {
        Parent ThreeCardParent = FXMLLoader.load(getClass().getResource("/client/view/NorthernPoker.fxml"));
        Scene ThreeCardScene = new Scene(ThreeCardParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ThreeCardScene);
    }
}
