package doctor;

import com.example.clinicapp.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import visit.VisitDao;
import visitTables.DoctorVisitTable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class DoctorController {
    @FXML
    public TableView <DoctorVisitTable> table = new TableView<>();
    @FXML
    public TableColumn<DoctorVisitTable, String> firstNameTable = new TableColumn<>();
    @FXML
    public TableColumn <DoctorVisitTable, String>lastNameTable= new TableColumn<>();
    @FXML
    public TableColumn <DoctorVisitTable, String>specializationTable= new TableColumn<>();
    @FXML
    public TableColumn<DoctorVisitTable, Timestamp> dateTable = new TableColumn<>();
    @FXML
    public Button closeButton;
    @FXML
    public Button deleteReservationButton;

    MainController mainController;
    Doctor doctor;

    public void logOutButtonAction() {
        mainController.loadLoginScreen();
    }

    @FXML
    public void closeButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setDoctorScreen(Doctor doctor) {
        this.doctor = doctor;
        setVisitTable();
    }

    private void setVisitTable() {
        List<DoctorVisitTable> listOfVisit = DoctorDao.getInstance().getVisitsToTable(doctor.getDoctor_id());
        setItemsInTableView(listOfVisit);
    }

    private void setItemsInTableView(List<DoctorVisitTable> arrayList) {
        ObservableList<DoctorVisitTable> doctorVisitTableList = FXCollections.observableArrayList(arrayList);
        firstNameTable.setCellValueFactory(new PropertyValueFactory<>("firstNamePatient"));
        lastNameTable.setCellValueFactory(new PropertyValueFactory<>("lastNamePatient"));
        specializationTable.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        dateTable.setCellValueFactory(new PropertyValueFactory<>("dateOfVisit"));
        table.setItems(doctorVisitTableList);
    }

    public void deleteReservationAction() {
        DoctorVisitTable doctorVisitTable = table.getSelectionModel().getSelectedItem();
        VisitDao.getInstance().deleteVisit(doctorVisitTable.getId());
        setVisitTable();
    }
}
