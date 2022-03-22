package visit;

import com.example.clinicapp.Patient;
import doctor.Doctor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "visit")
public class Visit {
    public Visit() {
    }

    @Id
    @Column(name = "visit_id")
    @GeneratedValue
    private int visitId;
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;
    @Column(name = "visit_date")
    private Timestamp visitDate;
}
