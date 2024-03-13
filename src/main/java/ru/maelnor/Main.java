package ru.maelnor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.maelnor.entity.Good;
import ru.maelnor.entity.Linked;
import ru.maelnor.entity.User;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        fillLinkedPurchaseList();
    }

    private static SessionFactory getSessionFactory() {
        try (StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build()) {
            Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        }
    }

    private static void fillLinkedPurchaseList() {
        SessionFactory sessionFactory = getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            List<Linked> linkedPurchases = session.createQuery(
                            "select new map(g as good, u as user) " +
                                    "from Purchase p " +
                                    "join Good g on p.goodName = g.name " +
                                    "join User u on p.userName = u.name", Map.class)
                    .getResultStream()
                    .map(map -> new Linked((Good) map.get("good"), (User) map.get("user")))
                    .toList();


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