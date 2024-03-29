package visitTables;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class PatientVisitTable {
    int id;
    String firstNameDoctor;
    String lastNameDoctor;
    String specialization;
    Timestamp dateOfVisit;
}
