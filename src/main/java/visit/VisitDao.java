package visit;

import com.example.clinicapp.HibernateUtil;
import com.example.clinicapp.PatientVisitTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class VisitDao {
    @SuppressWarnings("unchecked")
    public ArrayList<DoctorVisitTable> getAllVisitForDoctor(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<Visit> resultList = (ArrayList<Visit>) session.createQuery("select c from Visit c" +
                " WHERE c.doctor.doctor_id = :id").setParameter("id",id).getResultList(); //TODO poprawic
        session.close();
        ArrayList<DoctorVisitTable> arrayList = new ArrayList<>();
        for (Visit visit : resultList) {
            DoctorVisitTable doctorVisitTable = new DoctorVisitTable();
            doctorVisitTable.setFirstNamePatient(visit.getPatient().getFirstName());
            doctorVisitTable.setLastNamePatient(visit.getPatient().getLastName());
            doctorVisitTable.setSpecialization(visit.getDoctor().getSpecialisation());
            doctorVisitTable.setDateOfVisit(visit.getVisitDate());
            arrayList.add(doctorVisitTable);
        }
        return arrayList;
    }
@SuppressWarnings("unchecked")
    public List<PatientVisitTable> getAllVisitForPatient(int patientId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Visit> resultList = (ArrayList<Visit>) session.createQuery("select c from Visit c" +
                " WHERE c.patient.patientId = :patientId").setParameter("patientId", patientId ).getResultList();
        session.close();
        List<PatientVisitTable> arrayList = new ArrayList<>();
        for (Visit visit : resultList) {
            PatientVisitTable patientVisitTable = new PatientVisitTable();
            patientVisitTable.setFirstNameDoctor(visit.getPatient().getFirstName());
            patientVisitTable.setLastNameDoctor(visit.getPatient().getLastName());
            patientVisitTable.setSpecialization(visit.getDoctor().getSpecialisation());
            patientVisitTable.setDateOfVisit(visit.getVisitDate());
            arrayList.add(patientVisitTable);
        }
        return arrayList;
    }

    public void addNewVisitToDB(Visit newVisit) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(newVisit);
        session.getTransaction().commit();
        session.close();
    }
}
