package swt6.orm.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import swt6.orm.domain.Employee;

import java.util.function.Consumer;
import java.util.function.Function;

public class JpaUtil {
    private static EntityManagerFactory emFactory = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("WorkLogPU");
        }

        return emFactory;
    }

    public static void closeEntityManagerFactory() {
        if (emFactory != null) {
            emFactory.close();
            emFactory = null;
        }
    }

    public static EntityManager getTransactedEntityManager() {
        var em = getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        return em;
    }

    public static void commit(final EntityManager em) {
        var tx = em.getTransaction();
        if (tx.isActive()) {
            tx.commit();
        }
    }

    public static void rollback(final EntityManager em) {
        var tx = em.getTransaction();
        if (tx.isActive()) {
            tx.rollback();
        }
    }

    public static void executeInTransaction(final Consumer<EntityManager> callback) {
        try (EntityManager entityManager = JpaUtil.getTransactedEntityManager()) {
            try {
                callback.accept(entityManager);
                JpaUtil.commit(entityManager);
            } catch (Exception e) {
                JpaUtil.rollback(entityManager);
                throw e;
            }
        }
    }

    public static <T> T executeInTransaction(final Function<EntityManager, T> callback) {
        try (EntityManager entityManager = JpaUtil.getTransactedEntityManager()) {
            try {
                var result = callback.apply(entityManager);
                JpaUtil.commit(entityManager);
                return result;
            } catch (Exception e) {
                JpaUtil.rollback(entityManager);
                throw e;
            }
        }
    }
}
