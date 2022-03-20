package com.example.clinicapp;

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

    public String loginAndPasswordCheck(String email) {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Patient patient = (Patient) session.createQuery("SELECT c FROM Patient c" +
                            " WHERE email like :email")
                    .setParameter("email", "%" + email + "%").getSingleResult();
            session.close();
            return patient.getPassword();
        } catch (NoResultException e) {
            return "";
        }
    }
}
