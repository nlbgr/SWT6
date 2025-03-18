package swt6.spring.basics.ioc.logic.xmlconfig;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.util.Logger;

import java.util.*;

public class WorkLogServiceImpl implements WorkLogService {
  private Map<Long, Employee> employees = new HashMap<>();

  //@Setter
  @Autowired(required = true)
  private Logger logger;

  private void init() {
    employees.put(1L, new Employee(1L, "Bill", "Gates"));
    employees.put(2L, new Employee(2L, "James", "Goslin"));
    employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
  }
  
  public WorkLogServiceImpl() {
    init();
  }

  public WorkLogServiceImpl(Logger logger) {
    this();
    this.logger = logger;
  }

  @Override
  public Employee findEmployeeById(Long id) {
    Employee employee = employees.get(id);
    if (employee == null) {
      logger.log("No employee found with id " + id);
      throw new IllegalStateException("No employee found with id " + id);
    }
    logger.log("findEmployeeById(%s)".formatted(id == null ? "null" : id.toString()));
    return employee;
  }

  @Override
  public List<Employee> findAllEmployees() {
    logger.log("findAllEmployees() invoked");
    return new ArrayList<Employee>(employees.values());
  }
}
