package com.example.clinicapp;
import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name="patient")
public class    Patient {
    public Patient() {}
    @Id
    @Column(name="patient_id")
    @GeneratedValue
    private int patientId;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="pesel")
    private String pesel;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;


}
