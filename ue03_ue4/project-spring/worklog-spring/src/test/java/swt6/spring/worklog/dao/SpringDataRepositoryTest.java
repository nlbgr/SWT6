package swt6.spring.worklog.dao;

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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static swt6.util.PrintUtil.printTitle;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaConfig1.class)
public class SpringDataRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository; // Error highlighting weil IntelliJ bissl dumm is und die config Klasse mim Reposcan ned checkt


  @BeforeEach
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void before() {
    Employee empl1_ins = new Employee("Josefine", "Bacher", LocalDate.of(1970, 10, 26));
    employeeRepository.save(empl1_ins);
    Employee empl2_ins = new Employee("Kasimir", "Kleber", LocalDate.of(1970, 10, 26));
    employeeRepository.save(empl2_ins);
  }


  @Test
  @Transactional
  public void testSave() {
    Employee empl1 = employeeRepository.findAll().iterator().next();

    printTitle("update employee", 60, '-');
    empl1.setFirstName("Jaquira");
    empl1 = employeeRepository.save(empl1);
    System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));
  }

  @Test
  public void testFind() {
    printTitle("findById()", 60, '-');
    Optional<Employee> empl1 = employeeRepository.findById(4L);
    Optional<Employee> empl2 = employeeRepository.findById(5L);
    empl1.ifPresentOrElse(System.out::println, () -> System.out.println("not found"));
    empl2.ifPresentOrElse(System.out::println, () -> System.out.println("not found"));

    printTitle("findAll()", 60, '-');
    Iterable<Employee> employees = employeeRepository.findAll();
    employees.forEach(System.out::println);
  }

  @Test
  public void testQueries() {
    printTitle("findOldPeople()", 60, '-');
    var oldPeople = employeeRepository.findOlderThen(LocalDate.of(2000, 1, 1));
    oldPeople.forEach(System.out::println);
  }
}