package swt6.spring.worklog.dao;

import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void insert(T entity);
    T merge(T entity);
}
