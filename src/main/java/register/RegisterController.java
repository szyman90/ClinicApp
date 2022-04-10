package register;

import util.DialogWindows;
import com.example.clinicapp.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import patient.Patient;
import patient.PatientDao;

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

    @FXML
    public void createAccountAction() {
        if (fieldValidationCheck()) {
            createNewPatientAccount();
            DialogWindows.createAccountSuccessful();
            mainController.loadLoginScreen();
        }
    }

    public boolean fieldValidationCheck() {
        RegisterValidator registerValidator = new RegisterValidator();
        StringBuilder errorMessageString = new StringBuilder();
        if (!registerValidator.firstNameCheck(firstNameField))
            errorMessageString.append("first name; ");
        if (!registerValidator.lastNameCheck(lastNameField))
            errorMessageString.append("last name; ");
        if (!registerValidator.pesel(peselField))
            errorMessageString.append("PESEL; ");
        if (!registerValidator.street(streetField))
            errorMessageString.append("street; ");
        if (!registerValidator.houseNumber(houseNumberField))
            errorMessageString.append("house number; ");
        if (!registerValidator.flatHouseNumber(flatHouseField))
            errorMessageString.append("flat-house number; ");
        if (!registerValidator.emailCheck(emailField))
            errorMessageString.append("email; ");
        if (!registerValidator.password(passwordField, confirmPasswordField))
            errorMessageString.append("password;");
        if (errorMessageString.isEmpty())
            return true;
        else {
            DialogWindows.errorFromFields(errorMessageString.toString());
            return false;
        }
    }

    private void createNewPatientAccount() {
        Patient newPatient = new Patient();
        writeToPatientObject(newPatient);
        PatientDao.getInstance().addNewPatient(newPatient);
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