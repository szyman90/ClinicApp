module com.example.clinicapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.validator;
    requires static lombok;
    requires java.naming;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    opens com.example.clinicapp to javafx.fxml, org.hibernate.orm.core;
    exports com.example.clinicapp;
}
