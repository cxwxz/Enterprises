/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.HibernateImpl;

import com.mycompany.hibernatemvn.DB.DAO.EmployeeTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Denis
 */
//@Repository()
public class EmployeeTypeDAOImpl extends GenericDAOImpl<EmployeeType> implements EmployeeTypeDAO {

    public EmployeeTypeDAOImpl(){
        super(EmployeeType.class);
    }
    
    @Override
    public List<EmployeeType> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
    private HibernateUtils hibernateUtils;
    private static final Logger log = Logger.getLogger(EmployeeTypeDAOImpl.class);

    public EmployeeTypeDAOImpl(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public void add(EmployeeType employeeType) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.save(employeeType);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to save employeeType!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public EmployeeType getByName(String employeeTypeName) {

        Session session = hibernateUtils.getSession();
        try {

            Query employeeQuery = session.createQuery(
                    " from EmployeeType where name = :employeeTypeName");
            employeeQuery.setString("employeeTypeName", employeeTypeName);
            EmployeeType employee = (EmployeeType) employeeQuery.uniqueResult();

            session.close();
            return employee;
        } catch (HibernateException e) {
            log.error("Unable to get EmployeeType!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<EmployeeType> getList() {
        Session session = hibernateUtils.getSession();
        try {
            List<EmployeeType> list = session.createQuery("from EmployeeType").list();

            return list;
        } catch (HibernateException e) {
            log.error("Unable to get EmployeeTypeList!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(EmployeeType employeeTypeName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.delete(employeeTypeName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to close EmployeeType!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void update(EmployeeType employeeTypeName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.update(employeeTypeName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to update EmployeeType!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<EmployeeType> getByName(String employeeTypeName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmployeeType findById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
    
}
