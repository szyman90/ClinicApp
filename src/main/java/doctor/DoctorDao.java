package doctor;
import com.example.clinicapp.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.HibernateUtil;
import visit.Visit;
import visitTables.DoctorVisitTable;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao implements UserDao<Doctor, DoctorVisitTable> {
    private static DoctorDao instance;

    public static DoctorDao getInstance() {
        if (instance == null) {
            instance = new DoctorDao();
        }
        return instance;
    }

    private DoctorDao() {
    }

    public Doctor loginAndPasswordCheck(String email, String password) {
        Doctor doctor;
        try {
            if (email.equals("") || password.equals(""))
                return null;
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

    public List<DoctorVisitTable> getVisitsToTable(int DoctorId) {
        List<Visit> listOfVisit = getAllDoctorVisitsFromDB(DoctorId);
        return convertVisitToDoctorVisitTable(listOfVisit);
    }

    @SuppressWarnings("unchecked")
    private List<Visit> getAllDoctorVisitsFromDB(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<Visit> resultList = (ArrayList<Visit>) session.createQuery("select c from Visit c" +
                " WHERE c.doctor.doctor_id = :id").setParameter("id", id).getResultList();
        session.close();
        return resultList;
    }

    private List<DoctorVisitTable> convertVisitToDoctorVisitTable(List<Visit> listOfVisit) {
        List<DoctorVisitTable> arrayListForTable = new ArrayList<>();
        for (Visit visit : listOfVisit) {
            DoctorVisitTable doctorVisitTable = new DoctorVisitTable();
            doctorVisitTable.setFirstNamePatient(visit.getPatient().getFirstName());
            doctorVisitTable.setLastNamePatient(visit.getPatient().getLastName());
            doctorVisitTable.setSpecialization(visit.getDoctor().getSpecialisation());
            doctorVisitTable.setDateOfVisit(visit.getVisitDate());
            doctorVisitTable.setId(visit.getVisitId());
            arrayListForTable.add(doctorVisitTable);
        }
        return arrayListForTable;
    }

}
