package com.example.clinicapp;

import doctor.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import visit.VisitDao;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
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
    @FXML
    public Button addVisitButton;
    public TableColumn<PatientVisitTable, String> firstNameDoctorColumn = new TableColumn<>();
    public TableColumn<PatientVisitTable, String> lastNameDoctorColumn = new TableColumn<>();
    public TableColumn<PatientVisitTable, String> specializationColumn = new TableColumn<>();
    public TableColumn<PatientVisitTable, String> visitDateColumn = new TableColumn<>();
    public TableView<PatientVisitTable> table = new TableView<>();
    public ComboBox<String> specialistCombo;
    public ComboBox timeCombo;
    public DatePicker datePicker;
    public Pane addVisitPane;
    private Doctor doctorToAddVisit = null;
    private LocalDate dateForAddingVisit;

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
        addVisitPane.setVisible(false);
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
    } //TODO improve the appearance of the board

    public void addVisitButtonAction() {
        addVisitPane.setVisible(true);
        PatientDao patientDao = new PatientDao();
        ObservableList<String> specialistToCombo = patientDao.getSpecialist();
        specialistCombo.setItems(specialistToCombo);
    }

    public void takeSelectedDoctor() {
        String specialization =  specialistCombo.getValue();
        PatientDao patientDao = new PatientDao();
        doctorToAddVisit = patientDao.takeDoctor(specialization);
        //something is wrong with it
    }

    public void takeSelectedDate() {
        dateForAddingVisit = datePicker.getValue();
        setTimeCombo();
    }

    private void setTimeCombo() {
        PatientDao patientDao = new PatientDao();
        ArrayList<Timestamp> timestampOfVisitArrayList = patientDao.takeAllDoctorVisits(dateForAddingVisit, doctorToAddVisit);
        for(long i = 0; i < 16; i+=0.5) {
            //TODO adding to Combo, if there is no visit for that doctor

        }
    }
}

//TODO option for adding new visit or cancel visit