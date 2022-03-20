package com.example.clinicapp;

import javafx.scene.control.Alert;

public class DialogWindows {
    public static void wrongEmailOrPassword(String passwordFromTable) {
        if (passwordFromTable.equals(""))
            wrongEmail();
        else
            wrongPassword();
    }

    private static void wrongPassword() {
        Alert emailAlert = new Alert(Alert.AlertType.ERROR);
        emailAlert.setTitle("wrong password!");
        emailAlert.setContentText("Please enter the correct password");
        emailAlert.show();
    }

    private static void wrongEmail() {
        Alert emailAlert = new Alert(Alert.AlertType.ERROR);
        emailAlert.setTitle("Wrong email!");
        emailAlert.setContentText("There is no account with that email. Please enter correct email.");
        emailAlert.show();
    }

    public static void createAccountSuccessful() {
        Alert emailAlert = new Alert(Alert.AlertType.INFORMATION);
        emailAlert.setTitle("Done!");
        emailAlert.setContentText("Account create successful");
        emailAlert.show();
    }

    public static void errorFromFields(String fieldName) {
        Alert emailAlert = new Alert(Alert.AlertType.ERROR);
        emailAlert.setTitle("Wrong " + fieldName + "!");
        emailAlert.setContentText("Please enter the correct " + fieldName);
        emailAlert.showAndWait();
    }
}
