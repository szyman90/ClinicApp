package doctor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;

public class DoctorDao {
    private static DoctorDao instance;
    public static DoctorDao getInstance() {
        if (instance == null) {
            instance = new DoctorDao();
        }
        return instance;
    }
    private DoctorDao(){}
    public Doctor loginAndPasswordCheck(String email, String password) {
        Doctor doctor;
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            doctor = (Doctor) session.createQuery("SELECT c FROM Doctor c" +
                            " WHERE email = :email AND password = :password")
                    .setParameter("email", email).setParameter("password", password).getSingleResult();
            session.close();
        } catch (NoResultException e) {
            return null;
        }
        return doctor;
    }

}
