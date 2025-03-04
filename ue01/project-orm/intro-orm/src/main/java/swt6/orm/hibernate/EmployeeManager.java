package swt6.orm.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import swt6.orm.domain.Employee;
import swt6.orm.util.HibernateUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EmployeeManager {

    private static void saveEmployee(Employee employee) {
        try (Session session = HibernateUtil.getCurrentSession()) {
            Transaction tx = session.beginTransaction();

            session.persist(employee);

            tx.commit();
        }
    }

    private static List<Employee> getAllEmployees() {
        List<Employee> employeeList = null;
        try (Session session = HibernateUtil.getCurrentSession()) {
            Transaction tx = session.beginTransaction();

            Query<Employee> employeeListQuery = session.createQuery("select e from Employee e", Employee.class);
            employeeList = employeeListQuery.getResultList();

            tx.commit();
        }
        return employeeList;
    }

    static String promptFor(BufferedReader in, String prompt) {
        System.out.print(prompt + "> ");
        System.out.flush();
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, prompt);
        }
    }

    public static void main(String[] args) {
        var formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        var in = new BufferedReader(new InputStreamReader(System.in));
        String availCmds = "commands: quit, insert";

        System.out.println("Hibernate Employee Admin");
        System.out.println(availCmds);
        String userCmd = promptFor(in, "");

        try {
            while (!userCmd.equals("quit")) {
                try {
                    switch (userCmd) {
                        case "insert" -> {
                            saveEmployee(
                                    new Employee(
                                            promptFor(in, "firstName"),
                                            promptFor(in, "lastName"),
                                            LocalDate.parse(promptFor(in, "dateOfBirth (dd.mm.yyyy)"), formatter)
                                    )
                            );
                        }
                        case "list" -> {
                            for (var employee : getAllEmployees()) {
                                System.out.println(employee);
                            }
                        }
                        default -> {
                            System.out.println("ERROR: invalid command");
                            break;
                        }
                    } // switch
                } // try
                catch (NumberFormatException ignored) {
                    System.out.println("ERROR: Cannot parse integer");
                } catch (DateTimeParseException ignored) {
                    System.out.println("ERROR: Cannot parse date");
                }

                System.out.println(availCmds);
                userCmd = promptFor(in, "");
            } // while
        } // try
        catch (Exception ex) {
            ex.printStackTrace();
        } // catch
    }
}
