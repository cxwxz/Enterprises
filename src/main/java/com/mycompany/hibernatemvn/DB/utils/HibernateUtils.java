package com.mycompany.hibernatemvn.DB.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Denis
 */
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtils {

    private static final Logger log = Logger.getLogger(HibernateUtils.class);
     static SessionFactory sessionFactory=null;
  //  private static ServiceRegistry serviceRegistry;
    private static HibernateUtils hibernateUtils=null;

    private HibernateUtils() {
        try {
            log.info("SessionFactory is configurating...");
            sessionFactory = new Configuration().configure()
                    .buildSessionFactory();
            /*Configuration configuration = new Configuration().configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);*/
            log.info("Successful configuratation.");
        } catch (HibernateException e) {
            e.printStackTrace();
            log.error("Unable to create SessionFactory !!!", e);
        }
    }

    public static HibernateUtils getHibernateUtils() {
        if (hibernateUtils == null) {
            log.info("HibernateUtils initialization...");
            hibernateUtils = new HibernateUtils();
        }

        return hibernateUtils;
    }

    public Session getSession() {
        try {
            Session session = sessionFactory.openSession();
            log.info("Open session...");
            if (session == null) {
                throw new HibernateException("Session is null");
            }
            return session;
        } catch (HibernateException e) {
            log.error("Unable to open session", e);
            return null;
        }
    }

    public void close() {
        try {
            sessionFactory.close();
        } catch (HibernateException e) {
            log.error("Cannot close SessionFactory!", e);
        }
    }
}