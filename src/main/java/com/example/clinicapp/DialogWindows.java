package com.example.clinicapp;

import javafx.scene.control.Alert;

public class DialogWindows {
    public static void wrongEmailOrPassword() {
        Alert emailAlert = new Alert(Alert.AlertType.ERROR);
        emailAlert.setTitle("wrong e-mail or password!");
        emailAlert.setContentText("Please enter the correct data");
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
    } //TODO collect error and print all of them in one window
}
