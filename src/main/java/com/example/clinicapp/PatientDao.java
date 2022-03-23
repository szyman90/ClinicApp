package com.example.clinicapp;

import doctor.Doctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;

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

}
