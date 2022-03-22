package com.example.clinicapp;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class PatientVisitTable {
    String firstNameDoctor;
    String lastNameDoctor;
    String specialization;
    Timestamp dateOfVisit;
}
