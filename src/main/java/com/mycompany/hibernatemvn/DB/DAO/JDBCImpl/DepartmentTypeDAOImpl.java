/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl.*;
import com.mycompany.hibernatemvn.DB.DAO.DepartmentTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import com.mycompany.hibernatemvn.DB.utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedHashMap;
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
 * Implemention of departmenttype DAO interface.
 * Implemention uses JDBC level access to database.
 * 
 * @author Denis
 */
//@Repository()
public class DepartmentTypeDAOImpl extends GenericDAOImpl<DepartmentType> implements DepartmentTypeDAO {

    private static final String QUERY_INSERT_INTO_DEPARTMENTTYPE = "INSERT into DepartmentType values (NULL,?)";
    private static final String QUERY_UPDATE_DEPARTMENTTYPE = "UPDATE DepartmentType SET name=? WHERE id=?;";
    private static final String QUERY_UPDATE_DEPARTMENTTYPE_BY_ID = QUERY_SELECT_ALL_FROM_ENTITY + " where id = ?";
   
    DepartmentTypeDAOImpl() {
        super(DepartmentType.class);
    }

    DepartmentTypeDAOImpl(ConnectionManager connectionManager) {
        this();
        this.connectionManager = new ConnectionManager();
    }

    @Override
    public void save(DepartmentType type) {

        Connection connection = connectionManager.getConnection();
        
        try {
            
            PreparedStatement st = connection.prepareStatement(QUERY_INSERT_INTO_DEPARTMENTTYPE, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, type.getName());
            st.executeUpdate();
            
            ResultSet set = st.getGeneratedKeys();
            if (set.next()) {
                long id = set.getLong(1);
                type.setId(id);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            connectionManager.closeConnection();
        }

    }

    @Override
    public void update(DepartmentType type) {

        Connection connection = connectionManager.getConnection();

        try {
            
            PreparedStatement st = connection.prepareStatement(QUERY_UPDATE_DEPARTMENTTYPE);
            st.setString(1, type.getName());
            st.setLong(2, type.getId());
            st.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connectionManager.closeConnection();
        }

    }

    @Override
    public List<DepartmentType> findAll() {
        
        Connection connection = connectionManager.getConnection();
        try {
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(String.format(QUERY_SELECT_ALL_FROM_ENTITY, persistentClass.getSimpleName()));
            List<DepartmentType> types = DepartmentTypeCreator.formDepartmentTypes(rs);

            return types;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public DepartmentType findById(long id) {

        Connection connection = connectionManager.getConnection();

        try {

            // Getting entity by id
            PreparedStatement st = connection.prepareStatement(String.format(QUERY_UPDATE_DEPARTMENTTYPE_BY_ID, persistentClass.getSimpleName()));
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            DepartmentType type = DepartmentTypeCreator.formDepartmentType(rs);

            return type;
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public void delete(DepartmentType entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll(Collection<DepartmentType> entities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<DepartmentType> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DepartmentType> getSorted(String propertySortBy, boolean asc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
