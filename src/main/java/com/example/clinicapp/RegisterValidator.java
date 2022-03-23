package com.example.clinicapp;

import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

public class RegisterValidator {
    String FIRSTNAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]*";
    String LASTNAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][']?[A-Z]?[a-zżźćńółęąś]*[-]?[A-ZŻŹĆĄŚĘŁÓŃ]?[']?[A-Z]?[a-zżźćńółęąś]*";
    String NUMBER_REGEX = "[0-9]{1,4}[A-z]?";
    String STREET_REGEX = "[\\w\\s]+"; //TODO better pattern
    String PESEL_REGEX = "[0-9]{11}";

    RegisterValidator() {
    }

    public boolean firstNameCheck(TextField firstNameField) {

        if (!firstNameField.getText().matches(FIRSTNAME_REGEX) || firstNameField.getText().length() <= 1) {
            DialogWindows.errorFromFields("first name");
            firstNameField.clear();
            return false;
        }
        return true;
    }

    public boolean lastNameCheck(TextField lastNameField) {

        if (!lastNameField.getText().matches(LASTNAME_REGEX) || lastNameField.getText().length() <= 1) {
            DialogWindows.errorFromFields("last name");
            lastNameField.clear();
            return false;
        }
        return true;
    }

    public boolean emailCheck(TextField emailField) {
        if (emailField.getText().equals("")) {
            DialogWindows.errorFromFields("email"); //Czy zrobić z tego osobną funkcję
            emailField.clear();
            return false;
        } else if (emailField.getText().contains("clinic.com")) {
            DialogWindows.errorFromFields("email");
            emailField.clear();
            return false;
        } else if (!EmailValidator.getInstance().isValid(emailField.getText())) {
            DialogWindows.errorFromFields("email");
            emailField.clear();
            return false;
        }
        return true;
    }

    public boolean street(TextField streetField) {
        if (!streetField.getText().matches(STREET_REGEX)) {
            DialogWindows.errorFromFields("street");
            streetField.clear();
            return false;
        }
        return true;
    }

    public boolean houseNumber(TextField houseNumberField) {
        if (houseNumberField.getText().equals("") || !houseNumberField.getText().matches(NUMBER_REGEX)) {
            DialogWindows.errorFromFields("PESEL");
            houseNumberField.clear();
            return false;
        }
        return true;
    }

    public boolean flatHouseNumber(TextField flatHouseField) {
        if (!flatHouseField.getText().matches(NUMBER_REGEX) && !flatHouseField.getText().equals("")) {
            DialogWindows.errorFromFields("flat house number");
            flatHouseField.clear();
            return false;
        }
        return true;
    }

    public boolean pesel(TextField peselField) {
        if (peselField.getText().equals("") || !peselField.getText().matches(PESEL_REGEX)) {
            DialogWindows.errorFromFields("PESEL");
            peselField.clear();
            return false;
        }
        return true;
    } //TODO using a created by someone class to validate PESEL

    public boolean password(TextField passwordField, TextField confirmPasswordField) { //Tu też
        if (passwordField.getText().equals("") || (confirmPasswordField.getText().equals(""))) {
            DialogWindows.errorFromFields("password");
            passwordField.clear();
            confirmPasswordField.clear();
            return false;
        } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            DialogWindows.errorFromFields("password");
            passwordField.clear();
            confirmPasswordField.clear();
            return false;
        }
        return true;
    }
}
