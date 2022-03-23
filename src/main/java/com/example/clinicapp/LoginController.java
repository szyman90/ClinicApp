package com.example.clinicapp;

import doctor.Doctor;
import doctor.DoctorController;
import doctor.DoctorDao;
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
        if (email.contains("clinic.com"))
            verifyForDoctor(email, password);
        else
            verifyForPatient(email, password);

    }

    private void verifyForPatient(String email, String password) {
        try {
            PatientDao patientDao = new PatientDao();
            Patient patient = patientDao.loginAndPasswordCheck(email, password);
            patientScreenEnable(patient);
        } catch (NullPointerException e) {
            DialogWindows.wrongEmailOrPassword();
        }
    }

    private void verifyForDoctor(String email, String password) {
        try {
            DoctorDao doctorDao = new DoctorDao();
            Doctor doctor = doctorDao.loginAndPasswordCheck(email, password);
            doctorScreenEnable(doctor);
        } catch (NullPointerException e) {
            DialogWindows.wrongEmailOrPassword();
        }
    }

    private void doctorScreenEnable(Doctor doctor) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("doctorScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DoctorController doctorController = loader.getController();
        doctorController.setMainController(mainController);
        doctorController.setDoctor(doctor);
        mainController.setScreen(pane);
    }

    private void patientScreenEnable(Patient patient) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("patientScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PatientController patientController = loader.getController();
        patientController.setMainController(mainController);
        patientController.setPatient(patient);
        mainController.setScreen(pane);
    }
}