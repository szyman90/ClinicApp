package com.example.clinicapp;

import doctor.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import visit.Visit;

import javax.persistence.NoResultException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

public class PatientDao {
    public void addNewPatient(Patient newPatient) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(newPatient);
        session.getTransaction().commit();
        session.close();
    }

    public Patient loginAndPasswordCheck(String email, String password) {
        Patient patient;
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            patient = (Patient) session.createQuery("SELECT c FROM Patient c" +
                            " WHERE email = :email AND password = :password")
                    .setParameter("email",email).setParameter("password", password).getSingleResult();
            session.close();
        } catch (NoResultException e) {
            return null;
        }
        return patient;
    }
    @SuppressWarnings("unchecked")
    public ObservableList<String> getSpecialist() {
        ObservableList <String> specialization;
        ArrayList<String> list;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        list = (ArrayList<String>) session.createQuery("select specialisation from Doctor").getResultList();
        specialization = FXCollections.observableArrayList(list);
        session.close();
        return specialization;

    }

    public Doctor takeDoctor(String specialization) {
        Doctor doctor;
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            doctor = (Doctor) session.createQuery("SELECT c FROM Doctor c" +
                            " WHERE specialisation = :specialization")
                    .setParameter("specialization",specialization).getSingleResult();
            session.close();
        } catch (NoResultException e) {
            return null;
        }
        return doctor;
    }
    @SuppressWarnings("unchecked")
    public ArrayList<Timestamp> takeAllDoctorVisits(LocalDate dateForAddingVisit, Doctor doctor) {
        ArrayList<Timestamp> list;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        list = (ArrayList<Timestamp>) session.createQuery("select visitDate from Visit c" +
                " WHERE c.doctor.doctor_id = :doctor_id").setParameter("doctor_id",doctor.getDoctor_id())
                .getResultList();
        ArrayList<Timestamp> timestampArrayList = new ArrayList<>();
        for (Timestamp timestamp : list) {
            LocalDate timestampToLocalDate = timestamp.toLocalDateTime().toLocalDate();
            if (timestampToLocalDate.equals(dateForAddingVisit))
                timestampArrayList.add(timestamp);
        }
        session.close();
        return timestampArrayList;
    }

}
