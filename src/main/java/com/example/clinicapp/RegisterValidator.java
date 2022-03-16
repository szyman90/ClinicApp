package com.example.clinicapp;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class RegisterValidator {
    String FIRSTNAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]*";
    String LASTNAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]*[-]?[A-ZŻŹĆĄŚĘŁÓŃ]?[a-zżźćńółęąś]*";
    String NUMBER_REGEX = "[0-9]{1,4}[A-z]?";
    String STREET_REGEX = "[\\w\\s]+"; //TODO better pattern
    RegisterValidator() {
    }

    public boolean firstNameCheck(String firstName) {

        return firstName.matches(FIRSTNAME_REGEX) && firstName.length() > 1;
    }

    public boolean lastNameCheck(String lastName) {

        return lastName.matches(LASTNAME_REGEX) && lastName.length() > 1;
    }

    public boolean emailCheck(String email) {
        if (email.equals(""))
            return false;
        return EmailValidator.getInstance().isValid(email);
    }
    public boolean street(String street) {
        return street.matches(STREET_REGEX);
    }
    public boolean houseNumber(String houseNumber) {
        return houseNumber.matches(NUMBER_REGEX);
    }

    public boolean flatHouseNumber(String flatHouseNumber) {
        return flatHouseNumber.matches(NUMBER_REGEX);
    }

    public boolean pesel(String pesel) {
        if (pesel.equals(""))
            return false;
        return true;
    } //TODO using a created by someone class to validate PESEL

    public boolean password(String password, String confirmPassword) {
        if (password.equals("") || (confirmPassword.equals("")))
            return false;
        return password.equals(confirmPassword);
    }
}
