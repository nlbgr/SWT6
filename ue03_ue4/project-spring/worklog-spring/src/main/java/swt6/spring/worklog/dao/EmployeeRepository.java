package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Optional<Employee> findByLastName(String lastName);

    @Query("select e from Employee e where e.lastName like %:pattern%")
    List<Employee> findByLastNameContaining(@Param("pattern") String pattern);

    @Query("select e from Employee e where e.dateOfBirth < :date")
    List<Employee> findOlderThen(@Param("date") LocalDate date);
}
