package com.example.clinicapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    public void CloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void createAccountAction() {
        RegisterValidator registerValidator = new RegisterValidator();
        if (!registerValidator.firstNameCheck(firstNameField.getText())) {
            errorFromFields("first name");
            firstNameField.clear();
        } else if (!registerValidator.lastNameCheck(lastNameField.getText())) {
            errorFromFields("last name");
            lastNameField.clear();
        } else if (!registerValidator.emailCheck(emailField.getText())) {
            errorFromFields("email");
            emailField.clear();
        } else if (!registerValidator.houseNumber(houseNumberField.getText())) {
            errorFromFields("house number");
            houseNumberField.clear();
        } else if (!registerValidator.flatHouseNumber(flatHouseField.getText())) {
            errorFromFields("flat house number");
            flatHouseField.clear();
        } else if (!registerValidator.street(streetField.getText())) {
            errorFromFields("street");
            streetField.clear();
        } else if (!registerValidator.pesel(peselField.getText())) {
            errorFromFields("PESEL");
            peselField.clear();
        } else if (!registerValidator.password(passwordField.getText(), confirmPasswordField.getText())) {
            errorFromFields("password");
            passwordField.clear();
            confirmPasswordField.clear();
            }//TODO checking all field and register new account or throw exceptions
        }
        public void errorFromFields(String fieldName) {
        Alert emailAlert = new Alert(Alert.AlertType.ERROR);
        emailAlert.setTitle("incorrect "+ fieldName);
        emailAlert.setContentText("Please enter correct " + fieldName);
        emailAlert.showAndWait();
    }
}