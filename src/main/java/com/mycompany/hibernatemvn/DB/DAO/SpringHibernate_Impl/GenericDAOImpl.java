package com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl;

import com.mycompany.hibernatemvn.DB.DAO.GenericDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DomainObject;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.exceptions.DAOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generic DAO class that implements {@link IGenericDAO} methods
 *
 * @author Denis
 * @param <T> Type of entity to work with
 */
public abstract class GenericDAOImpl<T extends DomainObject> implements GenericDAO<T> {
    
    private static final String QUERY_SELECT_ALL = "SELECT x FROM %s x";
    private static final String QUERY_COUNT_ALL = "SELECT COUNT(x) FROM %s x";
    
    //protected static final Logger log = Logger.getLogger("DAO");
    /**
     * Spring's HibernateTemplate that is used to access db
     */
    @Autowired
    protected HibernateTemplate hibernateTemplate;
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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    protected <REZ> REZ executeQuery(String queryOrQueryName,
            boolean namedQuery, boolean singleResult, Object... parameters) {

        if (queryOrQueryName.isEmpty()) {
            throw new IllegalArgumentException(
                    "Query for executing cannot be null");
        }
        
        /*if (log.isDebugEnabled()) {
            log.debug(String.format("Executing query: '%s'; return single result: '%s'; query params: %s",
				     queryOrQueryName, singleResult,
						Arrays.toString(parameters)));
        }*/

        REZ result;
        List<?> list;
        //hibernateTemplate = null;
        if (namedQuery) {
            list = hibernateTemplate.findByNamedQuery(queryOrQueryName, parameters); //execute named query
        } else {
            list = hibernateTemplate.find(queryOrQueryName, parameters); //execute simple query
        }

        // Returning needed result
        if (singleResult) {
            if (list != null && !list.isEmpty()) {
                result = (REZ) list.get(0);
            } else {
                result = null;
            }
        } else {
            result = (REZ) list;
        }

        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public long getCount() {
        try {
            return executeQuery(String.format(QUERY_COUNT_ALL, persistentClass
                    .getSimpleName()), false, true);
        } catch (Exception e) {
           // log.error("Failed to get all entities count", e);
            return 0;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void save(T entity) {
        try {
            if (entity == null) {
                throw new IllegalArgumentException(
                        "Entity for saving cannot be null");
            }
            
           /* if (log.isEnabledFor(Priority.WARN)) {
            log.warn(String.format("Entity \"%s\" has id before been saved",
				     entity.getClass().getSimpleName()));
            }*/
            
            hibernateTemplate.save(entity);
            
        } catch (Exception e) {
            throw new DAOException("Failed to add" + entity.getClass().getSimpleName(), e, this);
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(T entity) {
        try {
            if (entity == null) {
                throw new IllegalArgumentException(
                        "Entity for updating cannot be null");
            }

            hibernateTemplate.update(entity);

        } catch (Exception e) {
            throw new DAOException("Failed to update" + entity.getClass().getSimpleName(), e, this);           
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(Long id) throws IllegalArgumentException {
        try {
            if (id == 0) {
                throw new IllegalArgumentException(
                        "Id for entity cannot be null!");
            }
            // Getting entity by id
            T savedEntity = findById(id);

            delete(savedEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(T entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException(
                    "Entity for saving cannot be null!");
        }

        if (entity.getId() == null) {
            throw new IllegalArgumentException(
                    "Trying to delete transient entity: " + entity);
        }

        try {
            if (entity instanceof Enterprise) {
                Enterprise enterprise = (Enterprise) entity;
                List<Department> deps = enterprise.getDepartments();
                List<Employee> emps = null;

                for (Department department : deps) {


                    emps = department.getEmployees();



                    for (Employee employee : emps) {
                        employee.getDepartments().remove(department);


                        hibernateTemplate.update(employee);

                    }

                    hibernateTemplate.delete(department);
               
                for (Employee employee : emps) {
                    if (employee.getDepartments().isEmpty()) {
                        hibernateTemplate.delete(employee);
                    }
                }
                }
                
                //if entity out of the session scope
           /* if (!hibernateTemplate.contains(entity)) {

                //Coping the state of the given object onto the persistent object with the same identifier.
                T attachedEntity = hibernateTemplate.merge(entity);
                hibernateTemplate.delete(attachedEntity);
            } else {*/
                hibernateTemplate.delete(enterprise);
           // }
            
            }
            else 
            {
         
                
            //if entity out of the session scope
            if (!hibernateTemplate.contains(entity)) {

                //Coping the state of the given object onto the persistent object with the same identifier.
                T attachedEntity = hibernateTemplate.merge(entity);
                hibernateTemplate.delete(attachedEntity);
            } else {
                hibernateTemplate.delete(entity);
            }
            
            
            }
        } catch (Exception e) {
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
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
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<T> findAll() {
        try {
            // find all entities
            List<T> list = executeQuery(String.format(QUERY_SELECT_ALL, persistentClass
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

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public T findById(long id) {

        try {

            // Getting entity by id
            T savedEntity = hibernateTemplate.get(persistentClass, id);

            // if the entity is NULL
            if ((savedEntity == null) || (savedEntity.getId() == null)) {

                return null;
            }

            return savedEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
