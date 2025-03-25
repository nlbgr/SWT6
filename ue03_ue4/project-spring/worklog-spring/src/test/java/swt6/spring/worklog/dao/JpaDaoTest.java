package swt6.spring.worklog.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.config.JpaConfig1;
import swt6.spring.worklog.domain.Employee;
import swt6.util.JpaUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static swt6.util.PrintUtil.printTitle;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaConfig1.class)
public class JpaDaoTest {

    @Autowired
    private EmployeeDao employeeDao;

    // only in V1 needed
    //@Autowired
    //private EntityManagerFactory emFactory;

    @BeforeEach
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void before() {
        Employee empl1_ins = new Employee("Josefine", "Bacher", LocalDate.of(1970, 10, 26));
        employeeDao.insert(empl1_ins);
        Employee empl2_ins = new Employee("Kasimir", "Kleber", LocalDate.of(1970, 10, 26));
        employeeDao.insert(empl2_ins);
    }

    @Test
    @Transactional
    public void testUpdate() {
        // V1
//        JpaUtil.executeInTransaction(emFactory, () -> {
//            printTitle("insert employee", 60, '-');
//            Employee empl1 = new Employee("Josefine", "Bacher", LocalDate.of(1970, 10, 26));
//            employeeDao.insert(empl1);
//            System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));
//
//            Employee empl2 = new Employee("Kasimir", "Kleber", LocalDate.of(1970, 10, 26));
//            employeeDao.insert(empl1);
//            System.out.println("empl2 = " + (empl2 == null ? (null) : empl2.toString()));
//
//            printTitle("update employee", 60, '-');
//            empl1.setFirstName("Jaquira");
//            empl1 = employeeDao.merge(empl1);
//            System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));
//        });

        Employee empl1 = employeeDao.findAll().getFirst();

        printTitle("update employee", 60, '-');
        empl1.setFirstName("Jaquira");
        empl1 = employeeDao.merge(empl1);
        System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));
    }

    @Test
    @Transactional
    public void testFindById() {
        printTitle("findById()", 60, '-');
        // ACHTUNG: sehr flaky, weil die IDs unterschiedlich vergeben weil ja inkrementiert wird. Nix gut diese
        // Maybe immer rollbacken nach den tests damits nicht so flaky ist
        Optional<Employee> empl1 = employeeDao.findById(4L);
        Optional<Employee> empl2 = employeeDao.findById(5L);
        empl1.ifPresentOrElse(System.out::println, () -> System.out.println("not found"));
        empl2.ifPresentOrElse(System.out::println, () -> System.out.println("not found"));
    }

    @Test
    @Transactional
    public void testFindAll() {
        printTitle("findAll()", 60, '-');
        List<Employee> employees = employeeDao.findAll();
        employees.forEach(System.out::println);
    }
}