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
    exports doctor;
    exports visit;
    opens doctor to javafx.fxml, org.hibernate.orm.core;
    opens visit to javafx.fxml, org.hibernate.orm.core;
    exports patient;
    opens patient to javafx.fxml, org.hibernate.orm.core;
    exports register;
    opens register to javafx.fxml, org.hibernate.orm.core;
    exports visitTables;
    opens visitTables to javafx.fxml, org.hibernate.orm.core;
    exports util;
    opens util to javafx.fxml, org.hibernate.orm.core;
}
