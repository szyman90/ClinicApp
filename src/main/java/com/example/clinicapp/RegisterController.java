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
//TODO cały field
        RegisterValidator registerValidator = new RegisterValidator();
        if (!registerValidator.firstNameCheck(firstNameField.getText())) {
            DialogWindows.errorFromFields("first name");
            firstNameField.clear();
        } else if (!registerValidator.lastNameCheck(lastNameField.getText())) {
            DialogWindows.errorFromFields("last name");
            lastNameField.clear();
        } else if (!registerValidator.pesel(peselField.getText())) {
            DialogWindows.errorFromFields("PESEL");
            peselField.clear();
        } else if (!registerValidator.street(streetField.getText())) {
            DialogWindows.errorFromFields("street");
            streetField.clear();
        } else if (!registerValidator.houseNumber(houseNumberField.getText())) {
            DialogWindows.errorFromFields("house number");
            houseNumberField.clear();
        } else if (!registerValidator.flatHouseNumber(flatHouseField.getText())) {
            DialogWindows.errorFromFields("flat house number");
            flatHouseField.clear();
        } else if (!registerValidator.emailCheck(emailField.getText())) {
            DialogWindows.errorFromFields("email");
            emailField.clear();
        } else if (!registerValidator.password(passwordField.getText(), confirmPasswordField.getText())) {
            DialogWindows.errorFromFields("password");
            passwordField.clear();
            confirmPasswordField.clear();
        } else {
            createNewPatientAccount();
            DialogWindows.createAccountSuccessful();
            mainController.loadLoginScreen();
        }
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