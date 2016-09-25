/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.HibernateImpl;

import com.mycompany.hibernatemvn.DB.DAO.DepartmentDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
//@Repository()
public class DepartmentDAOImpl extends GenericDAOImpl<Department> implements DepartmentDAO {

    public DepartmentDAOImpl(){
        super(Department.class);
    }
    
    /*@Override
    public List<Department> findByName(String name) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            // Getting entity by id
            List<Department> savedEntity =  executeQuery("findDepartmentByName", 
                     true, false, name);

            session.getTransaction().commit();
            return savedEntity;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }*/

    /*private HibernateUtils hibernateUtils;
    private static final Logger log = Logger.getLogger(DepartmentDAOImpl.class);

    public DepartmentDAOImpl(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public void add(Department department) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.save(department);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to save department!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Department getByName(String departmentName) {
        Session session = hibernateUtils.getSession();
        try {

            Query departmentQuery = session.createQuery(
                    " from Department where name = :departmentName");
            departmentQuery.setString("departmentName", departmentName);
            Department department = (Department) departmentQuery.uniqueResult();
            return department;
        } catch (HibernateException e) {
            log.error("Unable to get department!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Department> getList() {
        Session session = hibernateUtils.getSession();
        try {
            List<Department> list = session.createQuery("from Department").list();
            return list;
        } catch (HibernateException e) {
            log.error("Unable to get departmentlist!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(Department departmentName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.update(departmentName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to update Department!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void delete(Department departmentName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.delete(departmentName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to close Department!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<Department> getByName(String departmentName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Department findById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public List<Department> findByName(String name, long enterpriseId) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            // Getting entity by id
            List<Department> savedEntity =  executeQuery("findDepartmentByName", 
                     true, false, name, enterpriseId);

            session.getTransaction().commit();
            return savedEntity;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
    }
    }
}
