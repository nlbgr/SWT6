package swt6.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import swt6.orm.domain.Employee;

import java.time.LocalDate;

public class WorkLogManager {

    private static void insertEmployee(Employee employee) {
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

    public static void main(String[] args) {
        Employee employee1 = new Employee("Susi", "Müller", LocalDate.of(1998, 1, 1));
        Employee employee2 = new Employee("Franz", "Strolz", LocalDate.of(2000, 1, 1));

        insertEmployee(employee1);

    }
}
