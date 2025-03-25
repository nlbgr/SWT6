package swt6.spring.worklog.logic.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.logic.WorkLogService;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
public class WorkLogServiceImpl1 implements WorkLogService {

    private final EmployeeDao employeeDao;

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeDao.merge(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeDao.findAll();
    }
}
