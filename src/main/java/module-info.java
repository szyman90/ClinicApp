module com.example.clinicapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.validator;


    opens com.example.clinicapp to javafx.fxml;
    exports com.example.clinicapp;
}