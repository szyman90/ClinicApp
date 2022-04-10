package patient;

import com.example.clinicapp.MainController;
import visitTables.PatientVisitTable;
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

    // <editor-fold desc="Variables">
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
    @FXML
    public TableColumn<PatientVisitTable, String> lastNameDoctorColumn = new TableColumn<>();
    @FXML
    public TableColumn<PatientVisitTable, String> specializationColumn = new TableColumn<>();
    @FXML
    public TableColumn<PatientVisitTable, String> visitDateColumn = new TableColumn<>();
    @FXML
    public TableView<PatientVisitTable> table = new TableView<>();
    @FXML
    public ComboBox<String> specialistCombo;
    @FXML
    public ComboBox<LocalTime> timeCombo;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Pane addVisitPane;
    @FXML
    public Button addNewVisitButton;
    @FXML
    public Button makeReservationButton;
    @FXML
    public Button deleteReservationButton;
    @FXML
    private Doctor doctorToAddVisit = null;
    @FXML
    private LocalDate dateForAddingVisit = null;
    @FXML
    private LocalTime timeForAddingVisit = null;

    Visit visit = new Visit();
    MainController mainController;
    Patient patient;
// </editor-fold>

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPatientScreen(Patient patient) {
        this.patient = patient;
        welcomeLabel.setText("Welcome " + patient.getFirstName());
        setPersonalData();
        setVisitTable();
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        addVisitPane.setVisible(false);
        addNewVisitButton.setVisible(false);
    }

    @FXML
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
        List<PatientVisitTable> listOfVisit = PatientDao.getInstance().getVisitsToTable(patient.getPatientId());
        setItemsInTableView(listOfVisit);
    }

    private void setItemsInTableView(List<PatientVisitTable> arrayList) {
        ObservableList<PatientVisitTable> patientVisitTablesList = FXCollections.observableArrayList(arrayList);
        firstNameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("firstNameDoctor"));
        lastNameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("lastNameDoctor"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        visitDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfVisit"));
        table.setItems(patientVisitTablesList);
    }

    @FXML
    public void makeReservationAction() {
        addVisitPane.setVisible(true);
        ObservableList<String> specialistToCombo = PatientDao.getInstance().getSpecialist();
        specialistCombo.setItems(specialistToCombo);
    }

    @FXML
    public void addNewVisitAction() {
        visit.setPatient(this.patient);
        visit.setDoctor(this.doctorToAddVisit);
        LocalDateTime localDateTime = LocalDateTime.of(dateForAddingVisit, timeForAddingVisit);
        visit.setVisitDate(Timestamp.valueOf(localDateTime));
        VisitDao.getInstance().addNewVisitToDB(visit);
        addNewVisitButton.setVisible(false);
        addVisitPane.setVisible(false);
        setVisitTable();
    }
    @FXML
    public void deleteReservationAction() {
        PatientVisitTable patientVisitTable = table.getSelectionModel().getSelectedItem();
        VisitDao.getInstance().deleteVisit(patientVisitTable.getId());
        setVisitTable();
    }
    public void takeSelectedDoctor() {
        timeForAddingVisit = null;
        dateForAddingVisit = null;
        addNewVisitButton.setVisible(false);
        String specialization = specialistCombo.getValue();
        doctorToAddVisit = PatientDao.getInstance().getDoctor(specialization);
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
        List<Timestamp> visitsOnSelectedDayTimestampList = PatientDao
                .getInstance()
                .getAllDrVisitsFromDao(dateForAddingVisit, doctorToAddVisit);
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

}
