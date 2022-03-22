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
import visit.DoctorVisitTable;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DoctorController {
    public TableView <DoctorVisitTable> table = new TableView<>();
    public TableColumn<DoctorVisitTable, String> firstNameTable = new TableColumn<>();
    public TableColumn <DoctorVisitTable, String>lastNameTable= new TableColumn<>();
    public TableColumn <DoctorVisitTable, String>specializationTable= new TableColumn<>();
    public TableColumn<DoctorVisitTable, Timestamp> dateTable = new TableColumn<>();
    public Button closeButton;
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

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        setVisitTable();
    }

    private void setVisitTable() {
        VisitDao visitDao = new VisitDao();
        ArrayList<DoctorVisitTable> listOfVisit = visitDao.getAllVisitForDoctor(doctor.getDoctor_id());
        setItemsInTableView(listOfVisit);
    }

    private void setItemsInTableView(ArrayList<DoctorVisitTable> arrayList) {
        ObservableList<DoctorVisitTable> doctorVisitTableList = FXCollections.observableArrayList(arrayList);
        firstNameTable.setCellValueFactory(new PropertyValueFactory<>("firstNamePatient"));
        lastNameTable.setCellValueFactory(new PropertyValueFactory<>("lastNamePatient"));
        specializationTable.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        dateTable.setCellValueFactory(new PropertyValueFactory<>("dateOfVisit"));
        table.setItems(doctorVisitTableList);
    }
}
