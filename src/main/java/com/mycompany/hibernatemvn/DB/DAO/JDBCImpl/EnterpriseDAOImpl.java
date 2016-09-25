/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.EnterpriseDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.DB.utils.ConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 * Implemention of enterprise DAO interface.
 * Implemention uses JDBC level access to database.
 * 
 * @author Denis
 */
//@Repository()
public class EnterpriseDAOImpl extends GenericDAOImpl<Enterprise> implements EnterpriseDAO {

    private static final String QUERY_DELETE_UNEMPLOYED = "DELETE from employee where id not in (select employeeid from employees_departments)";
    private static final String QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS =
            "SELECT E.id, E.foundationDate, E.info, E.name, ET.id, ET.name, D.id, D.departmentName, DT.id, DT.name FROM Enterprise as E "
            + "LEFT JOIN enterprisetype as ET "
            + "ON E.Enterprisetypeid = ET.id "
            + "LEFT JOIN department as D "
            + "ON E.id = D.Enterpriseid "
            + "LEFT JOIN departmentType as DT "
            + "ON D.departmenttypeid = DT.id";
    
    private static final String QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS_BY_ID = 
            QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS + " where E.id = ?";
    
    private static final String QUERY_SELECT_ALL_ENTERPRISES_WITH_TYPES = 
            "SELECT E.id as enterpriseId, E.name, E.foundationDate, E.info,"
            + " ET.id as typeId, ET.name as typeName FROM Enterprise as E "
            + "LEFT JOIN EnterpriseType as ET on E.enterpriseTypeID = ET.id";
    
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_TYPES_IN_ENTERPRISE = 
            "SELECT D.id as departmentId, D.departmentName, "
            + "DT.id as typeId, DT.name as typeName FROM Department as D "
            + "LEFT JOIN DepartmentType as DT on D.departmentTypeID = DT.id where D.EnterpriseID = %d";
    
    private static final String QUERY_INSERT_INTO_ENTERPRISE = "INSERT into Enterprise values (NULL,?,?,?,?)";
    private static final String QUERY_UPDATE_ENTERPRISE = "UPDATE Enterprise SET name=?, foundationDate=?, info=?, EnterpriseTypeID=? WHERE id=?;";
 
    EnterpriseDAOImpl() {
        super(Enterprise.class);
    }

    EnterpriseDAOImpl(ConnectionManager connectionManager) {
        this();
        this.connectionManager = connectionManager;
    }

    /**
     * Saving basic data about enterprise. Inserted entities can't be save.
     * 
     * @param enterprise 
     */
    @Override
    public void save(Enterprise enterprise) {
        
   

        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement st = connection.prepareStatement(QUERY_INSERT_INTO_ENTERPRISE, Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, new java.sql.Date(enterprise.getFoundationDate().getYear(),
                                            enterprise.getFoundationDate().getMonth(), 
                                            enterprise.getFoundationDate().getDay()));
            st.setString(2, enterprise.getInfo());
            st.setString(3, enterprise.getName());
            st.setLong(4, enterprise.getType().getId());

            st.executeUpdate();
            ResultSet set = st.getGeneratedKeys();
            if (set.next()) {
                long id = set.getLong(1);
                enterprise.setId(id);
            }

        } catch (SQLException |NullPointerException ex) {
            Logger.getLogger(EnterpriseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public void update(Enterprise enterprise) {

        Connection connection = connectionManager.getConnection();

        try {

            //nullpointer (need to check
            connection.setAutoCommit(false);

            PreparedStatement st = connection.prepareStatement(QUERY_UPDATE_ENTERPRISE);
            st.setString(1, enterprise.getName());
            st.setDate(2, new java.sql.Date(enterprise.getFoundationDate().getYear(),
                                            enterprise.getFoundationDate().getMonth(),
                                            enterprise.getFoundationDate().getDay()));
            st.setString(3, enterprise.getInfo());
            st.setLong(4, enterprise.getType().getId());
            st.setLong(5, enterprise.getId());
            st.executeUpdate();
            st.close();// otherway statements will not be able to be used.            

            PreparedStatement st2 = connection.prepareStatement("UPDATE Department SET EnterpriseID=? WHERE id=?;");

            for (Department d : enterprise.getDepartments()) {
                st2.setLong(1, enterprise.getId());
                st2.setLong(2, d.getId());
                st2.addBatch();
            }
            st2.executeBatch();

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
                if (connection != null) 
                    connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(EnterpriseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connectionManager.closeConnection();
            }
        }

    }

    @Override
    public void delete(Enterprise enterprise) {

        Connection connection = connectionManager.getConnection();
 
        try {
            
            connection.setAutoCommit(false);
            
            PreparedStatement st1 = null;
            st1 = connection.prepareStatement("DELETE FROM employees_departments where DepartmentID = ?");          
            for (Department department : enterprise.getDepartments()) {
                st1.setLong(1, department.getId());
                st1.addBatch();   
            }
            st1.executeBatch();
            st1.close();
            
            PreparedStatement st2 = null;
            st2 = connection.prepareStatement(String.format(QUERY_DELETE_ENTITY, Department.class.getSimpleName()));
            for (Department department : enterprise.getDepartments()) {
                st2.setLong(1, department.getId());
                st2.addBatch();  
            }
            st2.executeBatch();
            st2.close();
            
            Statement st3 = connection.createStatement();
            st3.executeUpdate(QUERY_DELETE_UNEMPLOYED);
            st3.close();

            /* if(!enterprise.getDepartments().isEmpty()){
             throw new RuntimeException();
             * enterprise is transient !!!
             }*/
            
            PreparedStatement st4 = null;
            st4 = connection.prepareStatement(String.format(QUERY_DELETE_ENTITY, enterprise.getClass().getSimpleName()));
            st4.setLong(1, enterprise.getId());
            st4.executeUpdate();
            st4.close();
            
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
                if (connection != null)
                    connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(EnterpriseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connectionManager.closeConnection();
            }
        }
    }

    @Override
    public List<Enterprise> findAll() {

        Connection connection = connectionManager.getConnection();
        
        try {
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS);
            List<Enterprise> ents = EnterpriseCreator.formEnterprises(rs);
            return ents;

        } catch (SQLException ex) {
            Logger.getLogger(EnterpriseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();    
            return null;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public Enterprise findById(long id) {
        
        Connection connection = connectionManager.getConnection();
        
        try { 
            
            PreparedStatement st = connection.prepareStatement(QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS_BY_ID);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            List<Enterprise> l = EnterpriseCreator.formEnterprises(rs);
            Enterprise e = null;
         
            if(l.size() != 0)
              e = l.get(0);
     
            return e;
            
        } catch (SQLException ex) {
            Logger.getLogger(EnterpriseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(); 
            return null;
        } finally {
            connectionManager.closeConnection();
        }
    }

    @Override
    public List<Enterprise> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        try {
            /*if (id == 0) {
                throw new IllegalArgumentException(
                        "Id for entity cannot be null!");
            }
            // Getting entity by id*/
            Enterprise savedEntity = findById(id);

            delete(savedEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll(Collection<Enterprise> entities) {

        if (!entities.isEmpty()) {

            for (Enterprise entity : entities) {

                if (entity != null) {
                    
                    delete(entity);
                    
                } else {
                    //Entity is not found
                }
            }
        }
    }

    @Override
    public List<Enterprise> getSorted(String propertySortBy, boolean asc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
