package com.example.clinicapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    private MainController mainController;
    public PasswordField confirmPasswordField;
    public PasswordField passwordField;
    public TextField emailField;
    public TextField flatHouseField;
    public TextField houseNumberField;
    public TextField streetField;
    public TextField peselField;
    public TextField lastNameField;
    public TextField firstNameField;
    public Button closeButton;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
