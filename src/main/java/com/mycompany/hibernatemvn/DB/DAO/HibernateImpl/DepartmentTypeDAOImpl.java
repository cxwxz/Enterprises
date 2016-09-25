/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.HibernateImpl;

import com.mycompany.hibernatemvn.DB.DAO.DepartmentTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
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
public class DepartmentTypeDAOImpl extends GenericDAOImpl<DepartmentType> implements DepartmentTypeDAO {

    public DepartmentTypeDAOImpl(){
        super(DepartmentType.class);
    }
    
    @Override
    public List<DepartmentType> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*private HibernateUtils hibernateUtils;
    private static final Logger log = Logger.getLogger(DepartmentTypeDAOImpl.class);

    public DepartmentTypeDAOImpl(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public void add(DepartmentType departmentType) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.save(departmentType);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to save DepartmentType!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public DepartmentType getByName(String departmentTypeName) {
        Session session = hibernateUtils.getSession();
        try {

            Query departmentTypeQuery = session.createQuery(
                    " from DepartmentType where name = :departmentTypeName");
            departmentTypeQuery.setString("departmentTypeName", departmentTypeName);
            DepartmentType department = (DepartmentType) departmentTypeQuery.uniqueResult();
            return department;
        } catch (HibernateException e) {
            log.error("Unable to get departmentType!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<DepartmentType> getList() {
        Session session = hibernateUtils.getSession();
        try {
            List<DepartmentType> list = session.createQuery("from DepartmentType").list();
            return list;
        } catch (HibernateException e) {
            log.error("Unable to get departmentTypelist!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(DepartmentType departmentTypeName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.update(departmentTypeName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to update DepartmentType!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void delete(DepartmentType departmentTypeName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.delete(departmentTypeName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to close DepartmentType!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<DepartmentType> getByName(String departmentTypeName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DepartmentType findById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
