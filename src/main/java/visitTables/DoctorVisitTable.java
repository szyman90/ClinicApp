package visitTables;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class DoctorVisitTable {
    int id;
    String firstNamePatient;
    String lastNamePatient;
    String specialization;
    Timestamp dateOfVisit;
}
