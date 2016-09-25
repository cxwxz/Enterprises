/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.HibernateImpl;

import com.mycompany.hibernatemvn.DB.DAO.EnterpriseDAO;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

/**
 * Implemention of enterprise DAO interface
 * @author Denis
 */
//@Repository()
public class EnterpriseDAOImpl extends GenericDAOImpl<Enterprise> implements EnterpriseDAO {
    
    public EnterpriseDAOImpl(){
        super(Enterprise.class);
    }
    
    @Override
    public List<Enterprise> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
    private HibernateUtils hibernateUtils;
    private static final Logger log = Logger.getLogger(EnterpriseDAOImpl.class);


    public EnterpriseDAOImpl(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public void add(Enterprise enterprise) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.save(enterprise);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to save Enterprise!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

 /  @Override
    public List<Enterprise> getByName(String enterpriseName) {
        return null;

        Session session = hibernateUtils.getSession();
        try {

            Query enterpriseQuery = session.createQuery(
                    " from Enterprise where name = :enterpriseName");
            enterpriseQuery.setString("enterpriseName", enterpriseName);
            Enterprise enterprise = (Enterprise) enterpriseQuery.uniqueResult();

            //session.close();
            return enterprise;
        } catch (HibernateException e) {
            log.error("Unable to get Enterprise!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Enterprise> getList() {
        Session session = hibernateUtils.getSession();
        try {
            List<Enterprise> list = session.createQuery("from Enterprise").list();

            return list;
        } catch (HibernateException e) {
            log.error("Unable to get EnterpriseList!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Enterprise enterpriseName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.delete(enterpriseName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to close Enterprise!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void update(Enterprise enterpriseName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.update(enterpriseName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to update Enterprise!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<Enterprise> getByName(String enterpriseName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Enterprise findById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
