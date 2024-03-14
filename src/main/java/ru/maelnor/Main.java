package ru.maelnor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.maelnor.entity.Linked;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        fillLinkedPurchaseList();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Linked.class);
        configuration.configure();
        return configuration.buildSessionFactory();
    }

    private static void fillLinkedPurchaseList() {
        SessionFactory sessionFactory = getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            List<Linked> linkedPurchases = session.createQuery(
                            "select new Linked(g, u) " +
                                    "from Purchase p " +
                                    "join Good g on p.goodName = g.name " +
                                    "join User u on p.userName = u.name", Linked.class)
                    .getResultList();

            linkedPurchases.forEach(session::persist);

            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}