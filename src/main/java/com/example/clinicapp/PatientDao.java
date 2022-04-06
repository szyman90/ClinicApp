package com.example.clinicapp;

import doctor.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDao {
    private static PatientDao instance;

    private PatientDao() {}
    public static PatientDao getInstance() {
        if (instance == null) {
            instance = new PatientDao();
        }
        return instance;
    }
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
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<String> list = (ArrayList<String>) session.createQuery("select specialisation from Doctor").getResultList();
        ObservableList <String> specializationList = FXCollections.observableArrayList(list);
        session.close();
        return specializationList;

    }

    public Doctor getDoctor(String specialization) {
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
    public List<Timestamp> getAllDrVisitsFromDao(LocalDate dateForAddingVisit, Doctor doctor) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Timestamp> timestampOfVisitList = (ArrayList<Timestamp>) session.createQuery("select visitDate from Visit c" +
                " WHERE c.doctor.doctor_id = :doctor_id").setParameter("doctor_id",doctor.getDoctor_id())
                .getResultList();
        List<Timestamp> visitsOnSelectedDayList = new ArrayList<>();
        for (Timestamp timestamp : timestampOfVisitList) {
            LocalDate timestampVisitToLocalDate = timestamp.toLocalDateTime().toLocalDate();
            if (timestampVisitToLocalDate.equals(dateForAddingVisit))
                visitsOnSelectedDayList.add(timestamp);
        }
        session.close();
        return visitsOnSelectedDayList;
    }

}
