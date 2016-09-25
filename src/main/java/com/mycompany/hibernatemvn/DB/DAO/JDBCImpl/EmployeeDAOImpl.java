/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl.*;
import com.mycompany.hibernatemvn.DB.DAO.EmployeeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
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
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 * Implemention of employee DAO interface.
 * Implemention uses JDBC level access to database.
 * 
 * @author Denis
 */
//@Repository()
public class EmployeeDAOImpl extends GenericDAOImpl<Employee> implements EmployeeDAO {

    private static final String QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS =
            "SELECT E.id, E.firstName, E.lastName, E.birthday, ET.id, ET.name, D.id, D.departmentName, DT.id, DT.name FROM Employee as E "
            + "LEFT JOIN EmployeeType as ET on E.employeeTypeID = ET.id "
            + "LEFT JOIN employees_departments as ED on E.id = ED.employeeID LEFT JOIN department as D on D.id = ED.DepartmentId "
            + "LEFT JOIN DepartmentType as DT on D.DepartmentTypeID = DT.id";
    private static final String QUERY_INSERT_INTO_EMPLOYEE = "INSERT into Employee values (NULL,?,?,?,?)";
    private static final String QUERY_UPDATE_EMPLOYEE = "UPDATE Employee SET birthday=?, firstName=?, lastName=?, EmployeeTypeID=? WHERE id=?";
    private static final String QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS_BY_ID = QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS + " where E.id = ?";

    EmployeeDAOImpl() {
        super(Employee.class);
    }

    EmployeeDAOImpl(ConnectionManager connectionManager) {
        this();
        this.connectionManager = new ConnectionManager();
    }

    @Override
    public void save(Employee employee) {

        Connection connection = connectionManager.getConnection();

        try {


            PreparedStatement st = connection.prepareStatement(QUERY_INSERT_INTO_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
            st.setDate(1, new java.sql.Date(employee.getBirthday().getYear(),
                    employee.getBirthday().getMonth(),
                    employee.getBirthday().getDay()));
            st.setString(2, employee.getFirstName());
            st.setString(3, employee.getLastName());
            st.setLong(4, employee.getType().getId());
            st.executeUpdate();

            ResultSet set = st.getGeneratedKeys();
            if (set.next()) {
                long id = set.getLong(1);
                employee.setId(id);
            }
            st.close();

            connection = connectionManager.reOpenConnection();

            connection.setAutoCommit(false);

            updateManyToManyTable(employee);

            connection.commit();
            //? Exception throught
        } catch (SQLException ex) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            Logger.getLogger(EnterpriseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(EnterpriseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connectionManager.closeConnection();
            }
        }

    }

    private void updateManyToManyTable(Employee employee) throws SQLException {

        Connection connection = connectionManager.getConnection();

        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("INSERT into employees_departments values (?,?)");

            for (Department department : employee.getDepartments()) {


//check if employee is persistent
                st.setLong(1, employee.getId());
                st.setLong(2, department.getId());


                st.addBatch();
            }



            st.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("LOLOLOL");
        } finally {
            if (st != null) {
                st.close();
            }

        }

    }

    @Override
    public void update(Employee employee) {

        Connection connection = connectionManager.getConnection();

        try {

            PreparedStatement st = connection.prepareStatement(QUERY_UPDATE_EMPLOYEE);
            st.setDate(1, new java.sql.Date(employee.getBirthday().getYear(),
                    employee.getBirthday().getMonth(),
                    employee.getBirthday().getDay()));
            st.setString(2, employee.getFirstName());
            st.setString(3, employee.getLastName());
            st.setLong(4, employee.getType().getId());
            st.setLong(5, employee.getId());
            st.executeUpdate();
            st.close();

            updateManyToManyTable(employee);

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connectionManager.closeConnection();
        }
    }

    ////// TX  ///////
    @Override
    public void delete(Employee employee) {

        Connection connection = connectionManager.getConnection();

        try {

            connection.setAutoCommit(false);

            connection.createStatement().executeUpdate(String.format("DELETE FROM employees_departments where EmployeeID = %s", employee.getId()));

            PreparedStatement st = connection.prepareStatement(String.format(QUERY_DELETE_ENTITY, persistentClass
                    .getSimpleName()));
            st.setLong(1, employee.getId());
            st.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            Logger.getLogger(EnterpriseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(EnterpriseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connectionManager.closeConnection();
            }
        }
    }

    @Override
    public List<Employee> findAll() {

        Connection connection = connectionManager.getConnection();

        try {

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS);
            List<Employee> emps = EmployeeCreator.formEmployees(rs);
            return emps;

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public Employee findById(long id) {

        Connection connection = connectionManager.getConnection();

        try {

            PreparedStatement st = connection.prepareStatement(QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS_BY_ID);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            Employee employee = EmployeeCreator.formEmployees(rs).get(0);
            return employee;

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {

        try {
            /*if (id == 0) {
             throw new IllegalArgumentException(
             "Id for entity cannot be null!");
             }*/
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
}
