package com.example.clinicapp;

import javafx.fxml.FXML;
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

    //TODO add city etc.
    @FXML
    public void CloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void createAccountAction() {
        if (fieldValidationCheck()) {
            createNewPatientAccount();
            DialogWindows.createAccountSuccessful();
            mainController.loadLoginScreen();
        }
    }

    // @SuppressWarnings
    public boolean fieldValidationCheck() {
        RegisterValidator registerValidator = new RegisterValidator();
        if (!registerValidator.firstNameCheck(firstNameField)) {
            return false;
        } else if (!registerValidator.lastNameCheck(lastNameField)) {
            return false;
        } else if (!registerValidator.pesel(peselField)) {
            return false;
        } else if (!registerValidator.street(streetField)) {
            return false;
        } else if (!registerValidator.houseNumber(houseNumberField)) {
            return false;
        } else if (!registerValidator.flatHouseNumber(flatHouseField)) {
            return false;
        } else if (!registerValidator.emailCheck(emailField)) {
            return false;
        } else if (!registerValidator.password(passwordField, confirmPasswordField)) { //XDD
            return false;
        }
        return true;
    }

    private void createNewPatientAccount() {
        Patient newPatient = new Patient();
        writeToPatientObject(newPatient);
        PatientDao patientDao = new PatientDao();
        patientDao.addNewPatient(newPatient);
    }

    private void writeToPatientObject(Patient newPatient) {
        newPatient.setFirstName(firstNameField.getText());
        newPatient.setLastName(lastNameField.getText());
        newPatient.setEmail(emailField.getText());
        newPatient.setPesel(peselField.getText());
        newPatient.setPassword(passwordField.getText());
        newPatient.setStreet(streetField.getText());
        newPatient.setHouseNumber(houseNumberField.getText());
        newPatient.setFlatNumber(flatHouseField.getText());
    }


}