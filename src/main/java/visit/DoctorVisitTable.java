package visit;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class DoctorVisitTable {
    String firstNamePatient;
    String lastNamePatient;
    String specialization;
    Timestamp dateOfVisit;
}
