package swt6.spring.worklog.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeDaoJpa implements EmployeeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(em.find(Employee.class, id));
    }

    @Override
    public List<Employee> findAll() {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    @Override
    public void insert(Employee employee) {
        em.persist(employee);
    }

    @Override
    public Employee merge(Employee employee) {
        return em.merge(employee);
    }
}
