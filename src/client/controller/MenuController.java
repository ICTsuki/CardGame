package client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class MenuController {

    @FXML
    private Button button;
    @FXML
    private Button button2;

    public void ButtonHandleClick() {
        System.out.println("Button clicked!");
    }

    public void Button2HandleClick() {
        System.out.println("Button2 clicked!");
    }
}
