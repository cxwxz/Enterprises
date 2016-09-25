/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl.*;
import com.mycompany.hibernatemvn.DB.DAO.DepartmentDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.DB.utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 * Implemention of department DAO interface.
 * Implemention uses JDBC level access to database.
 * 
 * @author Denis
 */
//@Repository()
public class DepartmentDAOImpl extends GenericDAOImpl<Department> implements DepartmentDAO {

    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES =
            "SELECT D.id, D.departmentName, "
            + "DT.id as id1, DT.name as name1, "
            + "E.id as id3, E.firstName, E.lastName, E.birthday,"
            + "ET.id as id4, ET.name as name4 FROM Department as D "
            + "LEFT JOIN DepartmentType as DT on D.departmentTypeID = DT.id "
            + "LEFT JOIN employees_departments as ED on D.id = ED.DepartmentID "
            + "LEFT JOIN employee as E on E.id = ED.EmployeeId "
            + "LEFT JOIN EmployeeType as ET on E.EmployeeTypeID = ET.id";
    
    private static final String QUERY_DELETE_UNEMPLOYED = "delete from employee where id not in (select employeeid from employees_departments)";
    private static final String QUERY_INSERT_INTO_DEPARTMENT = "INSERT into Department values (NULL,?,?,?)";
    private static final String QUERY_UPDATE_DEPARTMENT = "UPDATE Department SET departmentName=?, DepartmentTypeID=? WHERE id=?;";
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_TYPES =
            "SELECT D.id as departmentId, D.departmentName, "
            + "DT.id as typeId, DT.name as typeName FROM Department as D "
            + "LEFT JOIN DepartmentType as DT on D.departmentTypeID = DT.id";
    
    /*private static final String QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS =
            "SELECT E.id, E.firstName, E.lastName, E.birthday, ET.id, ET.name, D.id, D.departmentName, DT.id, DT.name FROM Employee as E "
            + "LEFT JOIN EmployeeType as ET on E.employeeTypeID = ET.id "
            + "LEFT JOIN employees_departments as ED on E.id = ED.employeeID "
            + "LEFT JOIN department as D on D.id = ED.DepartmentId "
            + "LEFT JOIN DepartmentType as DT on D.DepartmentTypeID = DT.id";*/
    
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES_BY_dID = QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES + " where D.id = ?";
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES_BY_eID = QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES + " where E.id = ?";
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES_BY_NAME = QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES + " where D.departmentName = ? and D.enterpriseId = ?";
    
    
    DepartmentDAOImpl() {
        super(Department.class);
    }

    DepartmentDAOImpl(ConnectionManager connectionManager) {
        this();
        this.connectionManager = new ConnectionManager();
    }

    @Override
    public void save(Department department) {

        Connection connection = connectionManager.getConnection();

        try {
            
            PreparedStatement st = connection.prepareStatement(QUERY_INSERT_INTO_DEPARTMENT, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, department.getName());
            st.setLong(2, department.getType().getId());
            st.setLong(3, department.getEnterprise().getId());
            st.executeUpdate();

            ResultSet set = st.getGeneratedKeys();
            if (set.next()) {
                long id = set.getLong(1);
                department.setId(id);
            }

        } catch (SQLException ex) {
            //log
            ex.printStackTrace();

        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public void update(Department department) {

        Connection connection = connectionManager.getConnection();
        
        try {
            
            PreparedStatement st = connection.prepareStatement(QUERY_UPDATE_DEPARTMENT);
            st.setString(1, department.getName());
            st.setLong(2, department.getType().getId());
            st.setLong(3, department.getId());
            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public void delete(Department department) {

        Connection connection = connectionManager.getConnection();

        try {
            
            connection.setAutoCommit(false);
            
            PreparedStatement st1 = null;
            st1 = connection.prepareStatement("DELETE FROM employees_departments where DepartmentID = ?");
            st1.setLong(1, department.getId());
            st1.executeUpdate();
            st1.close();

            PreparedStatement st2 = null;
            st2 = connection.prepareStatement(String.format(QUERY_DELETE_ENTITY, persistentClass.getSimpleName()));
            st2.setLong(1, department.getId());
            st2.executeUpdate();

            /*st3 = connection.createStatement();   
             st3.executeUpdate(QUERY_DELETE_UNEMPLOYED);*/

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
            //log
            ex.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                connectionManager.closeConnection();
            }
        }
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        try {        
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

        Connection connection = connectionManager.getConnection();
        
        try {
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(QUERY_SELECT_ALL_DEPARTMENTS_WITH_TYPES);
            List<Department> deps = DepartmentCreator.formDepartments(rs);
            return deps;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public Department findById(long id) {

        Connection connection = connectionManager.getConnection();
        try {
            // Getting entity by id
            
            PreparedStatement st = connection.prepareStatement(QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES_BY_dID);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            Department department = DepartmentCreator.formDepartments(rs).get(0);

            List<Department> list;

            for (Employee e : department.getEmployees()) {
                st = connection.prepareStatement(QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES_BY_eID);
                st.setLong(1, e.getId());
                rs = st.executeQuery();
                list = DepartmentCreator.formDepartments(rs);
                e.setDepartments(list);
            }
            
            return department;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        } finally {
            connectionManager.closeConnection();
        }

    }

    @Override
    public List<Department> findByName(String name, long enterpriseId) {
        
        Connection connection = connectionManager.getConnection();
        
        try {
            
            PreparedStatement st = connection.prepareStatement(QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES_BY_NAME);
            st.setString(1, name);
            st.setLong(2, enterpriseId);
            ResultSet rs = st.executeQuery();

            List<Department> deps = DepartmentCreator.formDepartments(rs);
            return deps;

        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
            return null;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public List<Department> getSorted(String propertySortBy, boolean asc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
