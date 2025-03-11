package swt6.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import swt6.orm.domain.Employee;
import swt6.orm.util.JpaUtil;

import java.time.LocalDate;

public class WorkLogManager {
    private static void insertEmployee1(final Employee employee) {
        try (EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("WorkLogPU");
             EntityManager entityManager = emFactory.createEntityManager()) {

            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();
                entityManager.persist(employee);
                tx.commit();
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }

    private static void insertEmployee2(final Employee employee) {
        try (EntityManager entityManager = JpaUtil.getTransactedEntityManager()) {
            try {
                entityManager.persist(employee);
                JpaUtil.commit(entityManager);
            } catch (Exception e) {
                JpaUtil.rollback(entityManager);
                throw e;
            }
        }
    }

    private static void insertEmployee3(final Employee employee) {
        JpaUtil.executeInTransaction(entityManager -> {
            entityManager.persist(employee);
        });
    }

    public static <T> void insertEntity(final T entity) {
        JpaUtil.executeInTransaction(entityManager -> {
            entityManager.persist(entity);
        });
    }

    public static <T> T saveEntity(final T entity) {
        return JpaUtil.executeInTransaction(entityManager -> {
            return entityManager.merge(entity);
        });
    }

    public static void main(String[] args) {
        Employee employee1 = new Employee("Susi", "Müller", LocalDate.of(1998, 1, 1));
        Employee employee2 = new Employee("Franz", "Strolz", LocalDate.of(2000, 1, 1));

        try {
            System.out.println("------------- Creating Schema -------------");
            JpaUtil.getEntityManagerFactory();

            System.out.println("------------- Inserting employees -------------");
            insertEntity(employee1);
            insertEntity(employee2);

            System.out.println("------------- Saving entity -------------");
            employee1.setLastName("Müller-Huber");
            employee1 = saveEntity(employee1);
        } catch (Exception e) {
            JpaUtil.closeEntityManagerFactory();
        }
    }
}
