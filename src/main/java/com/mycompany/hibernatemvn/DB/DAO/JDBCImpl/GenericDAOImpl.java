package com.mycompany.hibernatemvn.DB.DAO.JDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.GenericDAO;
import com.mycompany.hibernatemvn.DB.Entities.DomainObject;
import com.mycompany.hibernatemvn.DB.utils.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
/**
 * Generic DAO class that implements {@link GenericDAO} methods. 
 * Implemention uses JDBC level access to database.
 *
 * @author Denis
 * @param <T> Type of entity to work with
 */
public abstract class GenericDAOImpl<T extends DomainObject> implements GenericDAO<T> {

    protected static final String QUERY_SELECT_ALL_FROM_ENTITY = "SELECT * FROM %s";
    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) AS Count FROM %s";//if '=' is better to use prepared statement
    protected static final String QUERY_DELETE_ENTITY = "DELETE from %s WHERE id=?";
    
    
    protected ConnectionManager connectionManager;
    
    protected static final Logger log = Logger.getLogger("DAO");
    
    /**
     * Persistent class that this dao works with
     */
    protected Class<T> persistentClass;

    private GenericDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    /**
     * Constructor with fields
     *
     * @param persistentClass Class that this dao will work with
     */
    public GenericDAOImpl(Class<T> persistentClass) {
        this();
        this.persistentClass = persistentClass;

    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    
    @Override
    public long getCount() {
        Connection connection = connectionManager.getConnection();
        try {
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(String.format(QUERY_COUNT_ALL, persistentClass
                    .getSimpleName()));
            if (rs.next()) {
                return rs.getLong("Count");
            }
            return 0;
        } catch (SQLException ex) {
            //log
            return 0;
        } finally {
            connectionManager.closeConnection();
        }

    }
}
