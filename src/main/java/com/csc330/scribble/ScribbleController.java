package com.csc330.scribble;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScribbleController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}