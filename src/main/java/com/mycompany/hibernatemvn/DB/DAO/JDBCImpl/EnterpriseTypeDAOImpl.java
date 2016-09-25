/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl.*;
import com.mycompany.hibernatemvn.DB.DAO.EnterpriseTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
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
 * Implemention of enterprisetype DAO interface.
 * Implemention uses JDBC level access to database.
 * 
 * @author Denis
 */
//@Repository()
public class EnterpriseTypeDAOImpl extends GenericDAOImpl<EnterpriseType> implements EnterpriseTypeDAO {

    
    private static final String QUERY_INSERT_INTO_ENTERPRISETYPE = "INSERT into EnterpriseType values (NULL,?)";
    private static final String QUERY_UPDATE_ENTERPRISETYPE = "UPDATE EnterpriseType SET name=? WHERE id=?;";
    private static final String QUERY_SELECT_ALL_FROM_ENTERPRISETYPE_BY_ID = QUERY_SELECT_ALL_FROM_ENTITY + " where id = ?";
            
    EnterpriseTypeDAOImpl() {
        super(EnterpriseType.class);
    }

    EnterpriseTypeDAOImpl(ConnectionManager connectionManager) {
        this();
        this.connectionManager = new ConnectionManager();
    }

    @Override
    public void save(EnterpriseType type) {

        Connection connection = connectionManager.getConnection();
        
        try {
            
            PreparedStatement st = connection.prepareStatement(QUERY_INSERT_INTO_ENTERPRISETYPE, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, type.getName());
            st.executeUpdate();
            
            ResultSet set = st.getGeneratedKeys();
            if (set.next()) {
                long id = set.getLong(1);
                type.setId(id);
            }
            
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();//check after dropped database
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public void update(EnterpriseType type) {
        Connection connection = connectionManager.getConnection();

        try {
            PreparedStatement st = connection.prepareStatement(QUERY_UPDATE_ENTERPRISETYPE);
            st.setString(1, type.getName());
            st.setLong(2, type.getId());
            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EnterpriseTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connectionManager.closeConnection();
        }

    }

    @Override
    public List<EnterpriseType> findAll() {

        Connection connection = connectionManager.getConnection();
        
        try {
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(String.format(QUERY_SELECT_ALL_FROM_ENTITY, persistentClass.getSimpleName()));
            List<EnterpriseType> types = EnterpriseTypeCreator.formEnterpriseTypes(rs);
            return types;
            
        } catch (SQLException ex) {
            Logger.getLogger(EnterpriseTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public EnterpriseType findById(long id) {

        Connection connection = connectionManager.getConnection();
        
        try {
            
            PreparedStatement st = connection.prepareStatement(String.format(QUERY_SELECT_ALL_FROM_ENTERPRISETYPE_BY_ID, persistentClass.getSimpleName()));
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            EnterpriseType type = EnterpriseTypeCreator.formEnterpriseType(rs);
            return type;
            
        } catch (SQLException ex) {
            Logger.getLogger(EnterpriseTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            connectionManager.closeConnection();
        }

    }

    @Override
    public List<EnterpriseType> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EnterpriseType> getSorted(String propertySortBy, boolean asc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(EnterpriseType entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll(Collection<EnterpriseType> entities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
