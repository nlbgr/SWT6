package swt6.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import swt6.orm.domain.*;
import swt6.orm.util.JpaUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    private static void listEmployees() {
        JpaUtil.executeInTransaction(entityManager -> {
            List<Employee> listEmplyees = entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();

            listEmplyees.forEach(employee -> {
                System.out.println(employee);

                if (employee.getAddress() != null) {
                    System.out.println("- Address: " + employee.getAddress());
                }

                if (!employee.getPhones().isEmpty()) {
                    System.out.println("- Phones: ");
                    employee.getPhones().forEach(phone -> {
                        System.out.println("\t" + phone);
                    });
                }

                if (!employee.getLogbookEntries().isEmpty()) {
                    System.out.println("- Logbook Entries: ");
                    employee.getLogbookEntries().forEach(log -> {
                        System.out.println("\t" + log);
                    });
                }
            });
        });
    }

    private static Employee addLogbookEntries(Employee employee, LogbookEntry... entries) {
        return JpaUtil.executeInTransaction(entityManager -> {
            Arrays.stream(entries).forEach(employee::addLogbookEntry);
            return entityManager.merge(employee);
        });
    }

    private static Employee addPhones(Employee employee, String... phones) {
        return JpaUtil.executeInTransaction(entityManager -> {
            Arrays.stream(phones).forEach(employee::addPhones);
            return entityManager.merge(employee);
        });
    }

    public static void main(String[] args) {
        PermanentEmployee pe1 = new PermanentEmployee("Susi", "Müller", LocalDate.of(1998, 1, 1));
        pe1.setSalary(5000.0);
        Employee employee1 = pe1;
        employee1.setAddress(new Address("4232", "Hagenberg City", "Softwarepark 13"));

        TemporaryEmployee te1 = new TemporaryEmployee("Franz", "Strolz", LocalDate.of(2000, 1, 1));
        te1.setHourlyRate(150.0);
        te1.setRenter("Dynatrace");
        te1.setStartDate(LocalDate.now());
        te1.setEndDate(LocalDate.now().plusMonths(10));
        Employee employee2 = te1;
        employee2.setAddress(new Address("1010", "Wien", "Partypulverstraße 69"));

        var today = LocalDate.now();
        LogbookEntry logbookEntry1 = new LogbookEntry("Codieren", today.atTime(8, 0), today.atTime(12, 0));
        LogbookEntry logbookEntry2 = new LogbookEntry("Testen", today.atTime(13, 0), today.atTime(15, 0));
        LogbookEntry logbookEntry3 = new LogbookEntry("Dokumentieren", today.atTime(15, 0), today.atTime(15, 30));

        try {
            System.out.println("------------- Creating Schema -------------");
            JpaUtil.getEntityManagerFactory();

            System.out.println("------------- Inserting employees -------------");
            insertEntity(employee1);
            insertEntity(employee2);

            System.out.println("------------- Saving entity -------------");
            employee1.setLastName("Müller-Huber");
            employee1 = saveEntity(employee1);

            System.out.println("------------- Add Logbook Entry -------------");
            employee1 = addLogbookEntries(employee1, logbookEntry1, logbookEntry2);
            employee2 = addLogbookEntries(employee2, logbookEntry3);

            System.out.println("------------- Add Phones -------------");
            employee2 = addPhones(employee2, "1234", "5678");

            System.out.println("------------- list Employees -------------");
            listEmployees();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.closeEntityManagerFactory();
        }
    }
}
