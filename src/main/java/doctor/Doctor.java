package doctor;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "doctor")
public class Doctor {
    public Doctor() {
    }

    @Id
    @Column(name = "doctor_id")
    @GeneratedValue
    private int doctor_id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "specialisation")
    private String specialisation;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
}
