package doctor;

import com.example.clinicapp.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;

public class DoctorDao {
    public String loginAndPasswordCheck(String email) {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Doctor doctor = (Doctor) session.createQuery("SELECT c FROM Doctor c" +
                            " WHERE email like :email")
                    .setParameter("email", "%" + email + "%").getSingleResult();
            session.close();
            return doctor.getPassword();
        } catch (NoResultException e) {
            return "";
        }
    }

    public Doctor getDoctorAfterLogin(String password) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Doctor doctor = (Doctor) session.createQuery("SELECT c FROM Doctor c" +
                        " WHERE password like :password")
                .setParameter("password", "%" + password + "%").getSingleResult();
        session.close();
        return doctor;
    }
}
