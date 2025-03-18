package swt6.spring.basics.ioc.logic.javaconfig;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.util.Log;
import swt6.spring.basics.ioc.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("worklogService")
public class WorkLogServiceImpl implements WorkLogService {
  private Map<Long, Employee> employees = new HashMap<>();

  @Autowired(required = true) // darf nur genau eine Impl passend zu dem typ geben
  @Log(Log.LoggerType.CONSOLE)
  private Logger logger;

  @PostConstruct // ersetzt init-method="init" im config file
  private void init() {
    employees.put(1L, new Employee(1L, "Bill", "Gates"));
    employees.put(2L, new Employee(2L, "James", "Goslin"));
    employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
  }

  @Autowired(required = true)
  public WorkLogServiceImpl(@Log(Log.LoggerType.CONSOLE) Logger logger) {
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
