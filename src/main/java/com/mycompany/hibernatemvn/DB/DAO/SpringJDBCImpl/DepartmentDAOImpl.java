/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringJDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.DepartmentDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class DepartmentDAOImpl implements DepartmentDAO {

    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) AS Count FROM Department";
    private static final String QUERY_DELETE_ENTITY = "DELETE from Department WHERE id = ?";
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES = "SELECT D.id, D.departmentName, DT.id, DT.name, E.id, E.firstName, E.lastName, E.birthday, ET.id, ET.name FROM Department as D LEFT JOIN DepartmentType as DT on D.departmentTypeID = DT.id LEFT JOIN employees_departments as ED on D.id = ED.DepartmentID LEFT JOIN employee as E on E.id = ED.EmployeeId LEFT JOIN EmployeeType as ET on E.EmployeeTypeID = ET.id";
    private static final String QUERY_DELETE_UNEMPLOYED = "delete from employee where id not in (select employeeid from employees_departments)";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @PostConstruct
    public void init() {
        simpleJdbcInsert.withTableName("Department").usingGeneratedKeyColumns("id");
    }

    @Override
    public long getCount() {

        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL, Long.class);

    }

    @Override
    public void save(Department department) {
        Map params = new LinkedHashMap();
        params.put("departmentName", department.getName());
        params.put("DepartmentTypeID", department.getType().getId());
        params.put("EnterpriseID", department.getEnterpriseId());
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        department.setId((Long) id);

    }

    @Override
    public void update(Department department) {

        String query = "UPDATE Department SET departmentName=?, DepartmentTypeID=? WHERE id=?;";

        jdbcTemplate.update(query, new Object[]{department.getName(),
            department.getType().getId(),
            department.getId()
        });

    }
    /////////////    TX       /////////////////////////////

    @Override
    public void delete(Department department) {

        String query1 = "DELETE FROM employees_departments where DepartmentID = ?";
        String query2 = QUERY_DELETE_ENTITY;

        jdbcTemplate.update(query1, department.getId());
        jdbcTemplate.update(query2, department.getId());


        jdbcTemplate.update(QUERY_DELETE_UNEMPLOYED);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        try {
            if (id == 0) {
                throw new IllegalArgumentException(
                        "Id for entity cannot be null!");
            }
            // Getting entity by id
            Department savedEntity = findById(id);

            delete(savedEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteAll(Collection<Department> entities) {

        if (!entities.isEmpty()) {

            for (Department entity : entities) {

                if (entity != null) {
                    delete(entity);
                } else {
                    //Entity is not found
                }
            }
        }
    }

    @Override
    public List<Department> findAll() {

        return jdbcTemplate.query(QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES, new DepartmentSetExtractor());

    }

    @Override
    public Department findById(long id) {

        Department department;

        List<Department> l = jdbcTemplate.query(QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES + " where D.id = ?", new Object[]{id}, new DepartmentSetExtractor());
        department = l.get(0);


        return department;

    }

   /* @Override
    public List<Department> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public List<Department> getSorted(String propertySortBy, boolean asc) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Department> findByName(String name, long enterpriseId) {
        
        return jdbcTemplate.query(QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES + " where D.departmentName = ? and D.enterpriseId = ?",  new Object[]{name, enterpriseId}, new DepartmentSetExtractor());

        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static final class DepartmentSetExtractor
            implements ResultSetExtractor<List<Department>> {

        @Override
        public List<Department> extractData(ResultSet rs)
                throws SQLException, DataAccessException {

            Map<Long, Department> map = new HashMap<Long, Department>();
            Department department = null;

            while (rs.next()) {

                Long departmentId = rs.getLong(1);

                if (departmentId == null) {
                    continue;
                }

                department = map.get(departmentId);

                if (department == null) {
                    department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString(2));
                    department.setType(new DepartmentType(rs.getLong(3), rs.getString(4)));

                    map.put(departmentId, department);

                }

                Long employeeId = rs.getLong(5);
                if (employeeId > 0) {
                    Employee employee = new Employee();
                    employee.setId(employeeId);
                    employee.setFirstName(rs.getString(6));
                    employee.setLastName(rs.getString(7));
                    employee.setBirthday(rs.getDate(8));
                    employee.setType(new EmployeeType(rs.getLong(9), rs.getString(10)));
                    department.setEmployees(employee);
                }
            }
            return new ArrayList<Department>(map.values());
        }
    }
}
