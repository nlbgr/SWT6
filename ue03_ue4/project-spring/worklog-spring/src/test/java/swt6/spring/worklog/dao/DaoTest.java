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
    public void testJdbc() {
        printTitle("create schema", 60, '-');
        createSchema(jdbcTemplate.getDataSource(), "worklog-db-schema.sql");

        printTitle("insert employee", 60, '-');
        Employee empl1 = new Employee("Josefine", "Mutzenbacher", LocalDate.of(1970, 10, 26));
        employeeDao.insert(empl1);
        System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

        printTitle("update employee", 60, '-');
        empl1.setFirstName("Jaquira");
        empl1 = employeeDao.merge(empl1);
        System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));
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
