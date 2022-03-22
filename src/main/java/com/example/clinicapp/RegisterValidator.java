package com.example.clinicapp;
import org.apache.commons.validator.routines.EmailValidator;

public class RegisterValidator {
    String FIRSTNAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]*";
    String LASTNAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][']?[A-Z]?[a-zżźćńółęąś]*[-]?[A-ZŻŹĆĄŚĘŁÓŃ]?[']?[A-Z]?[a-zżźćńółęąś]*";
    String NUMBER_REGEX = "[0-9]{1,4}[A-z]?";
    String STREET_REGEX = "[\\w\\s]+"; //TODO better pattern
    String PESEL_REGEX = "[0-9]{11}";
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
        if (email.contains("clinic.com"))
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
        return pesel.matches(PESEL_REGEX);
    } //TODO using a created by someone class to validate PESEL

    public boolean password(String password, String confirmPassword) {
        if (password.equals("") || (confirmPassword.equals("")))
            return false;
        return password.equals(confirmPassword);
    }
}
