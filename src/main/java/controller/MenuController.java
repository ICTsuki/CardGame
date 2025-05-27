package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.util.AudioManager;

import java.io.IOException;

public class MenuController {
    private String playerName1, playerName2, playerName3, playerName4;
    private int playerCount = 0;

    @FXML private TextField playerNameField1, playerNameField2, playerNameField3, playerNameField4;
    @FXML private ToggleButton musicToggleButton;

    public void TextNameField1Enter() {
        playerName1 = playerNameField1.getText(); playerCount++;
    }
    public void TextNameField2Enter() {
        playerName2 = playerNameField2.getText(); playerCount++;
    }
    public void TextNameField3Enter() {
        playerName3 = playerNameField3.getText(); playerCount++;
    }
    public void TextNameField4Enter() {
        playerName4 = playerNameField4.getText(); playerCount++;
    }

    public void ThreeCardButtonClick(ActionEvent event) throws IOException {
        if (playerCount < 2) {
            showAlert();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ThreeCardGame.fxml"));
        Parent root = loader.load();
        ThreeCardController controller = loader.getController();
        setPlayerNames(controller);
        switchScene(event, root);
    }

    public void SouthernPokerButtonClick(ActionEvent event) throws IOException {
        if (playerCount < 2) {
            showAlert();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SouthernPoker.fxml"));
        Parent root = loader.load();
        SouthernPokerController controller = loader.getController();
        setPlayerNames(controller);
        switchScene(event, root);
    }

    private void setPlayerNames(Object controller) {
        if (controller instanceof ThreeCardController tc) {
            if (playerName1 != null && !playerName1.isEmpty()) { tc.placePlayer(0, playerName1); tc.setPlayerName1(playerName1); }
            if (playerName2 != null && !playerName2.isEmpty()) { tc.placePlayer(1, playerName2); tc.setPlayerName2(playerName2); }
            if (playerName3 != null && !playerName3.isEmpty()) { tc.placePlayer(2, playerName3); tc.setPlayerName3(playerName3); }
            if (playerName4 != null && !playerName4.isEmpty()) { tc.placePlayer(3, playerName4); tc.setPlayerName4(playerName4); }
        } else if (controller instanceof SouthernPokerController np) {
            if (playerName1 != null && !playerName1.isEmpty()) { np.placePlayer(0, playerName1); np.setPlayerName1(playerName1); }
            if (playerName2 != null && !playerName2.isEmpty()) { np.placePlayer(1, playerName2); np.setPlayerName2(playerName2); }
            if (playerName3 != null && !playerName3.isEmpty()) { np.placePlayer(2, playerName3); np.setPlayerName3(playerName3); }
            if (playerName4 != null && !playerName4.isEmpty()) { np.placePlayer(3, playerName4); np.setPlayerName4(playerName4); }
        }
    }

    private void switchScene(ActionEvent event, Parent root) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "You need at least 2 players to start the game.");
        alert.setTitle("Game Error");
        alert.setHeaderText("Not Enough Players");
        alert.showAndWait();
    }

    @FXML public void initialize() {
        musicToggleButton.setSelected(AudioManager.getInstance().isPlaying());
        updateButtonText();
    }

    @FXML public void toggleMusic() {
        AudioManager.getInstance().toggle();
        updateButtonText();
    }

    private void updateButtonText() {
        musicToggleButton.setText(AudioManager.getInstance().isPlaying() ? "Pause Music" : "Play Music");
    }
}