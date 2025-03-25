package swt6.spring.worklog.logic.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.logic.WorkLogService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
public class WorkLogServiceImpl2 implements WorkLogService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }
}
