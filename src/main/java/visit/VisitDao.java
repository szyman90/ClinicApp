package visit;

import com.example.clinicapp.HibernateUtil;
import com.example.clinicapp.PatientVisitTable;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class VisitDao {
    private static VisitDao instance;
    private VisitDao(){}
    public static VisitDao getInstance() {
        if (instance == null) {
            instance = new VisitDao();
        }
        return instance;
    }
    public List<DoctorVisitTable> getDoctorVisitsToTable(int id) {
        List<Visit> listOfVisit = getAllDoctorVisitsFromDB(id);
        return convertVisitToDoctorVisitTable(listOfVisit);
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

    @SuppressWarnings("unchecked")
    private List<Visit> getAllDoctorVisitsFromDB(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<Visit> resultList = (ArrayList<Visit>) session.createQuery("select c from Visit c" +
                " WHERE c.doctor.doctor_id = :id").setParameter("id", id).getResultList();
        session.close();
        return resultList;
    }

    public List<PatientVisitTable> getPatientVisitsToTable(int patientId) {
        List<Visit> listOfVisit = getAllPatientVisitsFromDB(patientId);
        return convertVisitToPatientVisitTable(listOfVisit);
    }

    private List<PatientVisitTable> convertVisitToPatientVisitTable(List<Visit> listOfVisit) {
        List<PatientVisitTable> arrayListForTable = new ArrayList<>();
        for (Visit visit : listOfVisit) {
            PatientVisitTable patientVisitTable = new PatientVisitTable();
            patientVisitTable.setFirstNameDoctor(visit.getDoctor().getFirstName());
            patientVisitTable.setLastNameDoctor(visit.getDoctor().getLastName());
            patientVisitTable.setSpecialization(visit.getDoctor().getSpecialisation());
            patientVisitTable.setDateOfVisit(visit.getVisitDate());
            patientVisitTable.setId(visit.getVisitId());
            arrayListForTable.add(patientVisitTable);
        }
        return arrayListForTable;
    }
//TODO maybe builder
//TODO how to make visitTable classes better
//TODO maybe I can cast visit to table

    @SuppressWarnings("unchecked")
    private List<Visit> getAllPatientVisitsFromDB(int patientId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Visit> resultList = (ArrayList<Visit>) session.createQuery("select c from Visit c" +
                " WHERE c.patient.patientId = :patientId").setParameter("patientId", patientId).getResultList();
        session.close();
        return resultList;
    }

    public void addNewVisitToDB(Visit newVisit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(newVisit);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteVisit(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete from Visit where visitId = : id").setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
