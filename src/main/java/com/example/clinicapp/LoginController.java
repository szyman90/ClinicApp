package com.example.clinicapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private MainController mainController;
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button loginButton;
    @FXML
    public Button registerButton;
    @FXML
    private Button closeButton;

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handRegisterButton() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("registerScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegisterController registerController = loader.getController();
        registerController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    } // ale po co

    public void verifyLoginData() {
        String email = emailField.getText();
        String password = passwordField.getText();
        PatientDao patientDao = new PatientDao();
        String passwordFromTable = patientDao.loginAndPasswordCheck(email);
        if (passwordFromTable.equals(password))
            System.out.println("good");
        else
            DialogWindows.wrongEmailOrPassword(passwordFromTable);
    }
}