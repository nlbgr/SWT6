package swt6.spring.worklog.dao.jdbc;

import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoJdbc implements EmployeeDao {

    protected static class EmployeeRowMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee(
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(4).toLocalDate()
            );
            employee.setId(rs.getLong("id"));

            return employee;
        }
    }

//  @Setter
//  private DataSource dataSource;

    @Setter
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Employee> findById(Long id) {
        final String sql = "SELECT ID, FIRSTNAME, LASTNAME, DATEOFBIRTH FROM EMPLOYEE WHERE ID = ?";

        var employeeList = jdbcTemplate.query(sql, new Object[]{id}, new EmployeeRowMapper());

        if (employeeList.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, employeeList.size());
        }

        return employeeList.stream().findFirst();
    }

    @Override
    public List<Employee> findAll() {
        final String sql = "SELECT ID, FIRSTNAME, LASTNAME, DATEOFBIRTH FROM EMPLOYEE";

        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    // Version 1: Data access code without Spring
    //@Override
    public void insert1(final Employee e) throws DataAccessException {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";
        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getFirstName());
            stmt.setString(2, e.getLastName());
            stmt.setDate(3, Date.valueOf(e.getDateOfBirth()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Version 2: Use JdbcTemplate.update()
    //@Override
    public void insert2(final Employee e) throws DataAccessException {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";

        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setDate(3, Date.valueOf(e.getDateOfBirth()));
        });
    }

    // Version 3: User JdbcTemplate.update() kompaktere Version
    //@Override
    public void insert3(final Employee e) throws DataAccessException {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";

        jdbcTemplate.update(sql, e.getFirstName(), e.getLastName(), Date.valueOf(e.getDateOfBirth()));
    }

    // Version 4: User JdbcTemplate.update() kompaktere Version
    @Override
    public void insert(final Employee e) throws DataAccessException {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setDate(3, Date.valueOf(e.getDateOfBirth()));
            return ps;
        }, keyHolder);

        e.setId(keyHolder.getKey().longValue());
    }

    @Override
    public Employee merge(Employee entity) {
        return null;
    }
}
