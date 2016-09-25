/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.HibernateImpl;

import com.mycompany.hibernatemvn.DB.DAO.JDBCMongodb.*;
import com.mycompany.hibernatemvn.DB.DAO.GenericDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DomainObject;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;

/**
 * Generic DAO class that implements {@link GenericDAO} methods. 
 * Implemention uses JDBC level access to database.
 *
 * @author Denis
 * @param <T> Type of entity to work with
 */
public abstract class GenericDAOImpl<T extends DomainObject> implements GenericDAO<T> {

    private static final String QUERY_SELECT_ALL = "SELECT * FROM %s";
    private static final String QUERY_COUNT_ALL = "SELECT COUNT(x) FROM %s x";
    
    protected static final Logger log = Logger.getLogger("DAO");
    /**
     * Spring's HibernateTemplate that is used to access db
     */
    protected HibernateUtils hibernateUtils;

    /**
     * Persistent class that this dao works with
     */
    protected Class<T> persistentClass;

    /**
     * Constructor with fields
     *
     * @param persistentClass Class that this dao will work with
     */
    public GenericDAOImpl(Class<T> persistentClass) {

        this.persistentClass = persistentClass;
        
        this.hibernateUtils = HibernateUtils.getHibernateUtils();
    }

    /**
     * This template method executes select query with.
     * Methods sets parameters for the query as they appear in the parameters
     * list, by number starting from 1.
     * If
     * <code>singleResult = true</code> and no result is found, then
     * null is returned.
     * When multiple results are returned, they are being
     * dynamically casted to
     * REZ class. It should be able to cast to
     * {@link java.util.List}.
     *
     * @param <REZ> Class of the result
     * @param queryOrQueryName Query string or NamedQuery name
     * @param namedQuery Specifies, whether query is named query
     * @param singleResult Specifies, whether single result should be returned
     * @param parameters Parameters. Specifing multiple parameters
     * separated by comma
     * @return Result of the query
     * 
     */

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    protected <REZ> REZ executeQuery(String queryOrQueryName,
            boolean namedQuery, boolean singleResult, Object... parameters) {

        if (queryOrQueryName.isEmpty()) {
            throw new IllegalArgumentException(
                    "Query for executing cannot be null");
        }
  
        SQLQuery sQuery;
        Query query;
        REZ result;
        List<?> list;
   
        Session session = hibernateUtils.getSession();

        try {
            
            if (namedQuery) {
                    query = session.getNamedQuery(queryOrQueryName);//execute named query
            } else {
                sQuery = session.createSQLQuery(queryOrQueryName); //execute simple query
                sQuery.addEntity(persistentClass);
                query = sQuery;
            }
           
            if (parameters.length != 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i, parameters[i]);
                    
                }
            }
            list = query.list();
        
        
        //result
        if (singleResult) {
            if (!list.isEmpty()) {
                result = (REZ) list.get(0);
            } else {
                result = null;
            }
        } else {
            result = (REZ) list;
        }
     
        return result;
        
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
            
        } finally {
            if (session != null) {
                session.close();
            }
        }
        
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public long getCount() {
        try {
            return executeQuery(String.format(QUERY_COUNT_ALL, persistentClass
                    .getSimpleName()), false, true);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void save(T entity) {
        try {
            if (entity == null) {
                throw new IllegalArgumentException(
                        "Entity for saving cannot be null!");
            }


            Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Override
    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(T entity) {
        try {
            if (entity == null) {
                throw new IllegalArgumentException(
                        "Entity for saving cannot be null!");
            }



            Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }


        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(Long id) throws IllegalArgumentException {
        try {
            if (id == 0) {
                throw new IllegalArgumentException(
                        "Id for entity cannot be null!");
            }
            // Getting entity by id
            T savedEntity = findById(id);

            Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            session.delete(savedEntity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
            
            delete(savedEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(T entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException(
                    "Entity for saving cannot be null!");
        }

        if (entity.getId() == null) {
            throw new IllegalArgumentException(
                    "Trying to delete transient entity: " + entity);
        }
Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            
            if (entity instanceof Enterprise) {
                Enterprise enterprise = (Enterprise) entity;
                List<Department> deps = enterprise.getDepartments();
                List<Employee> emps = null;

                for (Department department : deps) {


                    emps = department.getEmployees();



                    for (Employee employee : emps) {
                        employee.getDepartments().remove(department);


                        session.update(employee);

                    }

                    session.delete(department);
               
                for (Employee employee : emps) {
                    if (employee.getDepartments().isEmpty()) {
                        session.delete(employee);
                    }
                }
                }
                
                session.delete(enterprise);
            }
            
            else{
                
            
            //if entity out of the session scope
            if (!session.contains(entity)) {
                //Coping the state of the given object onto the persistent object with the same identifier.
                T attachedEntity = (T) session.merge(entity);
                session.delete(attachedEntity);
            } else {
                session.delete(entity);
            }
            
            }
            
            session.getTransaction().commit();
        } catch (HibernateException e) {
        session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void deleteAll(Collection<T> entities) {

        if (!entities.isEmpty()) {

            for (T entity : entities) {

                if (entity != null) {
                    delete(entity);
                } else {
                    //Entity is not found
                }
            }
        }
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<T> findAll() {
        try {
            // find all entities
            List<T> list = (List<T>) executeQuery(String.format(QUERY_SELECT_ALL, persistentClass
                    .getSimpleName()), false, false);
            if (list != null) {
                return list;
            } else {
                return new ArrayList<T>();
            }

        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public T findById(long id) {
Session session = hibernateUtils.getSession();
        try {
            session.beginTransaction();
            // Getting entity by id
            T savedEntity = (T) session.get(persistentClass, id);

            // if the entity is NULL
            if ((savedEntity == null) || (savedEntity.getId() == null)) {

                return null;
            }
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

    /**
     * Appending sort information to query and returning new query
     *
     * @param query Query to append with sort information
     * @param propertySortBy Property to sort by
     * @param asc Specifies sort direction
     * @return new query with added sort
     */
    protected String addSortPropertyToQuery(String query, String propertySortBy,
            boolean asc) {
        StringBuilder sb = new StringBuilder();
        sb.append(query);
        sb.append(" ");
        sb.append("order by ");
        sb.append(propertySortBy);

        if (asc) {
            sb.append(" asc");
        } else {
            sb.append(" desc");
        }

        String newQuery = sb.toString();
        return newQuery;
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<T> getSorted(String propertySortBy, boolean asc) throws IllegalArgumentException {
        try {
            String query = String.format(QUERY_SELECT_ALL, persistentClass
                    .getSimpleName());

            String newQuery = addSortPropertyToQuery(query, propertySortBy, asc);

            return executeQuery(newQuery, false, false);
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }
 
}
