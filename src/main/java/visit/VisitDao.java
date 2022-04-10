package visit;

import util.HibernateUtil;
import org.hibernate.Session;


public class VisitDao {
    private static VisitDao instance;
    private VisitDao(){}
    public static VisitDao getInstance() {
        if (instance == null) {
            instance = new VisitDao();
        }
        return instance;
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
