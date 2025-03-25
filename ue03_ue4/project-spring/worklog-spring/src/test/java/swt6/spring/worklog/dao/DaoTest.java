package swt6.spring.worklog.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import swt6.spring.worklog.config.JdbcConfig;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DbScriptRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static swt6.util.PrintUtil.printTitle;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JdbcConfig.class)
public class DaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeDao employeeDao;

    private void createSchema(DataSource ds, String ddlScript) {
        try {
            DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
            InputStream is = DaoTest.class.getClassLoader().getResourceAsStream(ddlScript);
            if (is == null) throw new IllegalArgumentException(
                    String.format("File %s not found in classpath.", ddlScript));
            scriptRunner.runScript(new InputStreamReader(is));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() {
        printTitle("create schema", 60, '-');
        createSchema(jdbcTemplate.getDataSource(), "worklog-db-schema.sql");

        printTitle("insert employee", 60, '-');
        Employee empl1 = new Employee("Josefine", "Bacher", LocalDate.of(1970, 10, 26));
        employeeDao.insert(empl1);
        System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

        Employee empl2 = new Employee("Kasimir", "Kleber", LocalDate.of(1970, 10, 26));
        employeeDao.insert(empl2);
        System.out.println("empl2 = " + (empl2 == null ? (null) : empl2.toString()));

        // update is not implemented in JDBC
//        printTitle("update employee", 60, '-');
//        empl1.setFirstName("Jaquira");
//        empl1 = employeeDao.merge(empl1);
//        System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));
    }

    @Test
    public void testFind() {
        printTitle("create schema", 60, '-');
        createSchema(jdbcTemplate.getDataSource(), "worklog-db-schema.sql");


        Employee empl0 = new Employee("Josefine", "Bacher", LocalDate.of(1970, 10, 26));
        employeeDao.insert(empl0);
        System.out.println("empl1 = " + (empl0 == null ? (null) : empl0.toString()));


        printTitle("findAll()", 60, '-');
        List<Employee> employees = employeeDao.findAll();
        employees.forEach(System.out::println);

        printTitle("findById()", 60, '-');
        Optional<Employee> empl1 = employeeDao.findById(1L);
        Optional<Employee> empl2 = employeeDao.findById(9999L);
        empl1.ifPresentOrElse(System.out::println, () -> System.out.println("not found"));
        empl2.ifPresentOrElse(System.out::println, () -> System.out.println("not found"));
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testJpa() {
        try (AbstractApplicationContext factory =
                     new AnnotationConfigApplicationContext()) {

        }
    }

    @Test
    public void testSpringData() {
        try (AbstractApplicationContext factory =
                     new AnnotationConfigApplicationContext()) {

        }
    }
}
