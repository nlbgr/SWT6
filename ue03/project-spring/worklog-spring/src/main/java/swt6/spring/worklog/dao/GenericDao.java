package swt6.spring.worklog.dao;

import swt6.spring.worklog.domain.Employee;

import java.util.List;

public interface GenericDao<T, ID> {
    T findById(ID id);
    List<T> findAll();
    void insert(T entity);
    T merge(T entity);
}
