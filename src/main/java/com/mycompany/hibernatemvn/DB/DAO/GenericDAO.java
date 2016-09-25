package com.mycompany.hibernatemvn.DB.DAO;

import com.mycompany.hibernatemvn.DB.Entities.DomainObject;
import java.util.Collection;
import java.util.List;

/**
 * This is a generic interface that define generic methods used by all dao
 * classes. This methods include CRUD operations and other common operations
 * with database or other persistence mechanism.
 *
 * The methods in the interface can receive and return only data transfer
 * objects which extends {@link DomainObject}
 *
 * @author Denis
 *
 * @param <T> Domain class to work with
 */
public interface GenericDAO<T extends DomainObject> {
    

    /**
     * Save new object in database
     * 
     * @param entity object to store
     * @return 
     * @throws IllegalArgumentException 
     */
    public void save(T entity);

    /**
     * Getting all entities
     * 
     * @return List of entities or empty list if there are no entities or error
     */
    public List<T> findAll();

    /**
     * Deleting entity from database
     * 
     * @param entity object to delete
     */
    public void delete(T entity);

    /**
     * Updating entity in database
     * 
     * @param entity object to update
     */
    public void update(T entity);

    /**
     * Getting the entity by unique id
     * 
     * @param id id for serching
     * @return object of entity found by id 
     */
    public T findById(long id);

    /**
     * Deleting entity by id
     * 
     * @param id of entity to delete with
     */
    public void delete(Long id);

    /**
     * Deleting all entities by their ids
     * 
     * @param entities to delete
     */
    public void deleteAll(Collection<T> entities);

    /**
     * Getting number of rows from the table. Table is defined by <T> type.
     * 
     * @return the number of entities in the table
     */
    public long getCount();

    /**
	 * Retrieves from database all entities ordered by field specified by
	 * propertySortBy parameter.
	 * 
	 * @param propertySortBy
	 *            Name of property of entity class to order by.
	 * @param asc
	 *            Order direction: ascending if <code>true</code> and descending
	 *            otherwise.
	 * @return list of entities or empty list if there are no entities or error
	 */
    public List<T> getSorted(String propertySortBy, boolean asc);
}
