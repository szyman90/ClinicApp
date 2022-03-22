package com.example.clinicapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import visit.VisitDao;

import java.util.ArrayList;

public class PatientController {
    @FXML
    public Label welcomeLabel;
    @FXML
    public Label firstNameLabel;
    @FXML
    public Label lastNameLabel;
    @FXML
    public Label peselLabel;
    @FXML
    public Label emailLabel;
    @FXML
    public Label streetLabel;
    @FXML
    public Label houseNumberLabel;
    @FXML
    public Label flatNumberLabel;
    @FXML
    public Button closeButton;
    @FXML
    public Button logOutButton;
    public TableColumn<PatientVisitTable, String> firstNameDoctorColumn = new TableColumn<>();
    public TableColumn<PatientVisitTable, String> lastNameDoctorColumn = new TableColumn<>();
    public TableColumn<PatientVisitTable, String> specializationColumn = new TableColumn<>();
    public TableColumn<PatientVisitTable, String> visitDateColumn = new TableColumn<>();
    public TableView<PatientVisitTable> table = new TableView<>();
    MainController mainController;
    Patient patient;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        welcomeLabel.setText("Welcome " + patient.getFirstName());
        setPersonalData();
        setVisitTable();
    }

    public void logOutButtonAction() {
        mainController.loadLoginScreen();
    }

    @FXML
    public void closeButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void setPersonalData() {
        firstNameLabel.setText(patient.getFirstName());
        lastNameLabel.setText(patient.getLastName());
        peselLabel.setText(patient.getPesel());
        emailLabel.setText(patient.getEmail());
        streetLabel.setText(patient.getStreet());
        houseNumberLabel.setText(patient.getHouseNumber());
        if (patient.getFlatNumber() != null)
            flatNumberLabel.setText(patient.getFlatNumber());
        else
            flatNumberLabel.setText("");
    }

    private void setVisitTable() {
        VisitDao visitDao = new VisitDao();
        ArrayList<PatientVisitTable> listOfVisit = visitDao.getAllVisitForPatient(patient.getPatientId());
        setItemsInTableView(listOfVisit);
    }

    private void setItemsInTableView(ArrayList<PatientVisitTable> arrayList) {
        ObservableList<PatientVisitTable> patientVisitTablesList = FXCollections.observableArrayList(arrayList);
        firstNameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("firstNameDoctor"));
        lastNameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("lastNameDoctor"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        visitDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfVisit"));
        table.setItems(patientVisitTablesList);
    }

}

//TODO option for adding new visit or cancel visit