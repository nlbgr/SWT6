package swt6.spring.basics.ioc.logic.factorybased;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.util.Logger;
import swt6.spring.basics.ioc.util.LoggerFactory;

public class WorkLogServiceImpl {
  private Map<Long, Employee> employees = new HashMap<>();
  private Logger logger;

  private void initLogger() {
    Properties props = new Properties();
   
    try {
      ClassLoader cl = this.getClass().getClassLoader();
      props.load(cl.getResourceAsStream(
        "swt6/spring/basics/ioc/worklog.properties"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    // read logger type from configuration
    String loggerType =  props.getProperty("loggerType", "console");
    logger = LoggerFactory.getLogger(loggerType);
  }
  
  private void init() {
    employees.put(1L, new Employee(1L, "Bill", "Gates"));
    employees.put(2L, new Employee(2L, "James", "Goslin"));
    employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
  }
  
  public WorkLogServiceImpl() {
    initLogger();
    init();
  }


  public Employee findEmployeeById(Long id) {
    Employee employee = employees.get(id);
    if (employee == null) {
      logger.log("No employee found with id " + id);
      throw new IllegalStateException("No employee found with id " + id);
    }
    logger.log("findEmployeeById(%s)".formatted(id == null ? "null" : id.toString()));
    return employee;
  }

  public List<Employee> findAllEmployees() {
    logger.log("findAllEmployees() invoked");
    return new ArrayList<Employee>(employees.values());
  }
}
