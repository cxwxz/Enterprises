/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.HibernateImpl;

import com.mycompany.hibernatemvn.DB.DAO.EnterpriseTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Implemention of enterprisetype DAO interface
 * @author Denis
 */
//@Repository()
public class EnterpriseTypeDAOImpl extends GenericDAOImpl<EnterpriseType> implements EnterpriseTypeDAO {

    public EnterpriseTypeDAOImpl(){
        super(EnterpriseType.class);
    }
    
    @Override
    public List<EnterpriseType> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   /* private HibernateUtils hibernateUtils;
    private static final Logger log = Logger.getLogger(EnterpriseDAOImpl.class);

    public EnterpriseTypeDAOImpl(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public void add(EnterpriseType enterpriseType) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.save(enterpriseType);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to save EnterpriseType!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<EnterpriseType> getByName(String enterpriseTypeName) {
        return null;

        Session session = hibernateUtils.getSession();
        try {

            Query enterpriseQuery = session.createQuery(
                    " from EnterpriseType where name = :enterpriseTypeName");
            enterpriseQuery.setString("enterpriseTypeName", enterpriseTypeName);
            EnterpriseType enterprise = (EnterpriseType) enterpriseQuery.uniqueResult();

            session.close();
            return enterprise;
        } catch (HibernateException e) {
            log.error("Unable to get EnterpriseType!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<EnterpriseType> getList() {
        Session session = hibernateUtils.getSession();
        try {
            List<EnterpriseType> list = session.createQuery("from EnterpriseType").list();

            return list;
        } catch (HibernateException e) {
            log.error("Unable to get EnterpriseTypeList!", e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(EnterpriseType enterpriseTypeName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.delete(enterpriseTypeName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to close EnterpriseType!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void update(EnterpriseType enterpriseTypeName) {
        Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.update(enterpriseTypeName);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Unable to update EnterpriseType!", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<EnterpriseType> getByName(String enterpriseTypeName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EnterpriseType findById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
