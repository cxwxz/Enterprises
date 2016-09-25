/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringJDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.EmployeeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
//@Repository()
public class EmployeeDAOImpl implements EmployeeDAO {

    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) AS Count FROM Employee";
    private static final String QUERY_DELETE_ENTITY = "DELETE from Employee WHERE id = ?";
    private static final String QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS = "SELECT E.id, E.firstName, E.lastName, E.birthday, ET.id, ET.name, D.id, D.departmentName, DT.id, DT.name FROM Employee as E LEFT JOIN EmployeeType as ET on E.employeeTypeID = ET.id LEFT JOIN employees_departments as ED on E.id = ED.employeeID LEFT JOIN department as D on D.id = ED.DepartmentId LEFT JOIN DepartmentType as DT on D.DepartmentTypeID = DT.id";  
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @PostConstruct
    public void init() {
        simpleJdbcInsert.withTableName("Employee").usingGeneratedKeyColumns("id");
    }

    @Override
    public long getCount() {

        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL, Long.class);

    }

    @Override
    public void save(Employee employee) {
        Map params = new LinkedHashMap();
        params.put("firstName", employee.getFirstName());
        params.put("lastName", employee.getLastName());
        params.put("birthday", new java.sql.Date(employee.getBirthday().getYear(), employee.getBirthday().getMonth(), employee.getBirthday().getDay()));
        params.put("EmployeeTypeID", employee.getType().getId());
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        employee.setId((Long) id);

        updateManyToManyTable(employee);

    }

    private void updateManyToManyTable(Employee employee) {

        Iterator<Department> it = null;
        if (!employee.getDepartments().isEmpty()) {

            it = employee.getDepartments().iterator();

        } else {
            return;
        }

        while (it.hasNext()) {

            String query = "INSERT into employees_departments values (?,?)";
            try {
                jdbcTemplate.update(query, employee.getId(), it.next().getId());
            } catch (DataAccessException e) {
            }

        }
    }

    @Override
    public void update(Employee employee) {
     
        

        String query = "UPDATE Employee SET birthday=?, firstName=?, lastName=?, EmployeeTypeID=? WHERE id=?";

        jdbcTemplate.update(query, new Object[]{
            new java.sql.Date(employee.getBirthday().getYear(), employee.getBirthday().getMonth(), employee.getBirthday().getDay()),
            employee.getFirstName(),
            employee.getLastName(),
            employee.getType().getId(),
            employee.getId()
        });

        updateManyToManyTable(employee);

    }

    ////// TX  ///////
    @Override
    public void delete(Employee employee) {

        String query1 = "DELETE FROM employees_departments where EmployeeID = ?";
        String query2 = QUERY_DELETE_ENTITY;

        jdbcTemplate.update(query1, employee.getId());
        jdbcTemplate.update(query2, employee.getId());

    }

    @Override
    public List<Employee> findAll() {

        return jdbcTemplate.query(QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS, new EmployeeSetExtractor());

    }

    @Override
    public Employee findById(long id) {
        Employee employee;

        List<Employee> l = jdbcTemplate.query(QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS + " where E.id = ?", new Object[]{id}, new EmployeeSetExtractor());
        employee = l.get(0);


        return employee;
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        try {
            if (id == 0) {
                throw new IllegalArgumentException(
                        "Id for entity cannot be null!");
            }
            // Getting entity by id
            Employee savedEntity = findById(id);

            delete(savedEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteAll(Collection<Employee> entities) {

        if (!entities.isEmpty()) {

            for (Employee entity : entities) {

                if (entity != null) {
                    delete(entity);
                } else {
                    //Entity is not found
                }
            }
        }
    }

    @Override
    public List<Employee> findByFullName(String firstName, String lastName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Employee> getSorted(String propertySortBy, boolean asc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static final class EmployeeSetExtractor
            implements ResultSetExtractor<List<Employee>> {

        @Override
        public List<Employee> extractData(ResultSet rs)
                throws SQLException, DataAccessException {

            Map<Long, Employee> map = new HashMap<Long, Employee>();
            Employee employee = null;

            while (rs.next()) {

                Long employeeId = rs.getLong(1);

                if (employeeId == null) {
                    continue;
                }

                employee = map.get(employeeId);

                if (employee == null) {
                    employee = new Employee();
                    employee.setId(employeeId);
                    employee.setFirstName(rs.getString(2));
                    employee.setLastName(rs.getString(3));
                    employee.setBirthday(rs.getDate(4));
                    employee.setType(new EmployeeType(rs.getLong(5), rs.getString(6)));

                    map.put(employeeId, employee);

                }

                Long departmentId = rs.getLong(7);
                if (employeeId > 0) {
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString(8));
                    department.setType(new DepartmentType(rs.getLong(9), rs.getString(10)));
                    employee.setDepartments(department);
                }
            }
            return new ArrayList<Employee>(map.values());
        }
    }
}
