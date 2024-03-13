package ru.maelnor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.maelnor.entity.Linked;

import java.util.List;

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
                            "select new ru.maelnor.entity.Linked(c.id, s.id) " +
                                    "from Purchase pl " +
                                    "join Good c on pl.goodName = c.name " +
                                    "join User s on pl.userName = s.name", Linked.class)
                    .getResultList();


            // Сохраняем все объекты LinkedPurchaseList
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