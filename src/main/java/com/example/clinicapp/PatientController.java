package com.example.clinicapp;

import doctor.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import visit.Visit;
import visit.VisitDao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    final long WORK_START_TIME = 28800;
    final long WORK_END_TIME = 28800 * 2;
    final long HALF_HOUR_IN_SEC = 1800;
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
    public TableColumn<PatientVisitTable, String> firstNameDoctorColumn = new TableColumn<>();
    public TableColumn<PatientVisitTable, String> lastNameDoctorColumn = new TableColumn<>();
    public TableColumn<PatientVisitTable, String> specializationColumn = new TableColumn<>();
    public TableColumn<PatientVisitTable, String> visitDateColumn = new TableColumn<>();
    public TableView<PatientVisitTable> table = new TableView<>();
    public ComboBox<String> specialistCombo;
    public ComboBox<LocalTime> timeCombo;
    public DatePicker datePicker;
    public Pane addVisitPane;
    public Button addNewVisitButton;
    public Button makeReservationButton;
    private Doctor doctorToAddVisit = null;
    private LocalDate dateForAddingVisit = null;
    private LocalTime timeForAddingVisit = null;

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
        addNewVisitButton.setVisible(false);
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
        List<PatientVisitTable> listOfVisit = visitDao.getAllVisitForPatient(patient.getPatientId());
        setItemsInTableView(listOfVisit);
    }

    private void setItemsInTableView(List<PatientVisitTable> arrayList) {
        ObservableList<PatientVisitTable> patientVisitTablesList = FXCollections.observableArrayList(arrayList);
        firstNameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("firstNameDoctor"));
        lastNameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("lastNameDoctor"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        visitDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfVisit"));
        table.setItems(patientVisitTablesList);
    } //TODO improve the appearance of the board

    public void makeReservationAction() {
        addVisitPane.setVisible(true);
        PatientDao patientDao = new PatientDao();
        ObservableList<String> specialistToCombo = patientDao.getSpecialist();
        specialistCombo.setItems(specialistToCombo);
    }

    public void takeSelectedDoctor() {
        timeForAddingVisit = null;
        dateForAddingVisit = null;
        addNewVisitButton.setVisible(false);
        String specialization = specialistCombo.getValue();
        PatientDao patientDao = new PatientDao();
        doctorToAddVisit = patientDao.getDoctor(specialization);
        //something is wrong with it
    }

    public void takeSelectedDate() {
        addNewVisitButton.setVisible(false);
        timeForAddingVisit = null;
        dateForAddingVisit = datePicker.getValue();
        setTimeCombo();
    }

    private void setTimeCombo() {
        List<LocalTime> drVisitsOnSelectedDayList = getAllDoctorVisitsOnSelectedDay();
        ObservableList<LocalTime> drFreeAppointmentList = FXCollections.observableList(getAllFreeAppointment(drVisitsOnSelectedDayList));
        timeCombo.setItems(drFreeAppointmentList);
    }

    private List<LocalTime> getAllFreeAppointment(List<LocalTime> allDoctorVisits) {
        List<LocalTime> drFreeTimeList = new ArrayList<>();
        for (long i = WORK_START_TIME; i < WORK_END_TIME; i += HALF_HOUR_IN_SEC) {
            LocalTime localTimeBuff = LocalTime.ofSecondOfDay(i);
            if (!allDoctorVisits.contains(localTimeBuff))
                drFreeTimeList.add(localTimeBuff);
        }
        return drFreeTimeList;
    }

    private List<LocalTime> getAllDoctorVisitsOnSelectedDay() {
        PatientDao patientDao = new PatientDao();
        List<Timestamp> visitsOnSelectedDayTimestampList = patientDao.getAllDrVisitsFromDao(dateForAddingVisit, doctorToAddVisit);
        return toLocalTimeList(visitsOnSelectedDayTimestampList);
    }

    private List<LocalTime> toLocalTimeList(List<Timestamp> visitsOnSelectedDayList) {
        List<LocalTime> toLocalTimeList = new ArrayList<>();
        for (Timestamp timestamp : visitsOnSelectedDayList) {
            toLocalTimeList.add(timestamp.toLocalDateTime().toLocalTime());
        }
        return toLocalTimeList;
    }

    public void takeSelectedTime() {
        this.timeForAddingVisit = timeCombo.getValue();
        addNewVisitButton.setVisible(true);
    }

    public void addNewVisitAction() {
        Visit newVisit = new Visit();
        newVisit.setPatient(this.patient);
        newVisit.setDoctor(this.doctorToAddVisit);
        LocalDateTime localDateTime = LocalDateTime.of(dateForAddingVisit, timeForAddingVisit);
        newVisit.setVisitDate(Timestamp.valueOf(localDateTime));
        VisitDao visitDao = new VisitDao();
        visitDao.addNewVisitToDB(newVisit);
        addNewVisitButton.setVisible(false);
        addVisitPane.setVisible(false);
    }

}

//TODO option for cancel visit
//TODO don't let users make reservation in the past