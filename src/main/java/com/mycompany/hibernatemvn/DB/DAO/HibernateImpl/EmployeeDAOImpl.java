/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.HibernateImpl;

import com.mycompany.hibernatemvn.DB.DAO.EmployeeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
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
public class EmployeeDAOImpl extends GenericDAOImpl<Employee> implements EmployeeDAO {

    public EmployeeDAOImpl(){
        super(Employee.class);
    }
    
    @Override
    public List<Employee> findByFullName(String firstName, String lastName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   /* private HibernateUtils hibernateUtils;
    private static final Logger log = Logger.getLogger(EmployeeDAOImpl.class);

    public EmployeeDAOImpl(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public void add(Employee employee) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to save Employee!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Employee getByName(String employeeName) {
        Session session = hibernateUtils.getSession();
        try {

            Query employeeQuery = session.createQuery(
                    " from Employee where name = :employeeName");
            employeeQuery.setString("employeeName", employeeName);
            Employee employee = (Employee) employeeQuery.uniqueResult();
            session.getTransaction().commit();
            return employee;
        } catch (HibernateException e) {
            log.error("Unable to get employee!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<Employee> getList() {
        Session session = hibernateUtils.getSession();
        try {
            List<Employee> list = session.createQuery("from Employee").list();
            return list;
        } catch (HibernateException e) {
            log.error("Unable to get employeelist!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Employee employeeName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.delete(employeeName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to close Employee!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void update(Employee employeeName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.update(employeeName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to update Employee!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<Employee> getByName(String employeeName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Employee findById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
   
}
