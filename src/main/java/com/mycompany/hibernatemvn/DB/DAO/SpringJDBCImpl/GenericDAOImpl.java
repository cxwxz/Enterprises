/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringJDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl.*;
import com.mycompany.hibernatemvn.DB.DAO.GenericDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import com.mycompany.hibernatemvn.DB.Entities.DomainObject;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
import com.mycompany.hibernatemvn.DB.utils.ConnectionManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Generic DAO class that implements {@link IGenericDAO} methods
 *
 * @author Denis
 * @param <T> Type of entity to work with
 */
public abstract class GenericDAOImpl<T extends DomainObject> implements GenericDAO<T> {
    
    //private static final String QUERY_SELECT_ALL = "SELECT x FROM %s x";
    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) AS EntCount FROM %s";
    private static final String QUERY_DELETE_ENTITY = "DELETE from %s WHERE id=?";
    //private static final String QUERY_SELECT_ALL = "SELECT * FROM Enterprise as E LEFT JOIN EnterpriseType as ET on E.enterpriseTypeID = ET.id LEFT JOIN Department as D on E.id = D.EnterpriseID LEFT JOIN Employees_Departments as ED on D.id = ED.DepartmentID LEFT JOIN Employee as EM on EM.id = ED.EmployeeID;"; 
    private static final String QUERY_SELECT_ALL_ENTERPRISES_WITH_TYPES = "SELECT E.id as enterpriseId, E.name, E.foundationDate, E.info, ET.id as typeId, ET.name as typeName FROM Enterprise as E LEFT JOIN EnterpriseType as ET on E.enterpriseTypeID = ET.id"; 
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_TYPES = "SELECT D.id as departmentId, D.departmentName, DT.id as typeId, DT.name as typeName FROM Department as D LEFT JOIN DepartmentType as DT on D.departmentTypeID = DT.id"; 
    private static final String QUERY_SELECT_ALL_EMPLOYEES_WITH_TYPES = "SELECT E.id as employeeId, E.firstName, E.lastName, E.birthday, ET.id as typeId, ET.name as typeName FROM Employee as E LEFT JOIN EmployeeType as ET on E.employeeTypeID = ET.id";     
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES = "SELECT D.id, D.departmentName, DT.id, DT.name, E.id, E.firstName, E.lastName, E.birthday, ET.id, ET.name FROM Department as D LEFT JOIN DepartmentType as DT on D.departmentTypeID = DT.id LEFT JOIN employees_departments as ED on D.id = ED.DepartmentID LEFT JOIN employee as E on E.id = ED.EmployeeId LEFT JOIN EmployeeType as ET on E.EmployeeTypeID = ET.id;"; 
    private static final String QUERY_SELECT_DEPARTMENTS_IN_ENTERPRISE = "SELECT * FROM Enterprise as E LEFT JOIN EnterpriseType as ET on E.enterpriseTypeID = ET.id LEFT JOIN Department as D on E.id = D.EnterpriseID LEFT JOIN Employees_Departments as ED on D.id = ED.DepartmentID LEFT JOIN Employee as EM on EM.id = ED.EmployeeID;"; 
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_TYPES_IN_ENTERPRISE = "SELECT D.id as departmentId, D.departmentName, DT.id as typeId, DT.name as typeName FROM Department as D LEFT JOIN DepartmentType as DT on D.departmentTypeID = DT.id where D.EnterpriseID = %d";
    private static final String QUERY_SELECT_ALL_EMPLOYEES_WITH_TYPES_IN_DEPARTMENT = "SELECT E.id as employeeId, E.firstName, E.lastName, E.birthday, ET.id as typeId, ET.name as typeName FROM Employee as E LEFT JOIN EmployeeType as ET on E.employeeTypeID = ET.id where E.id in (select EmployeeID from employees_departments where DepartmentID = %d)";     
    private static final String QUERY_SELECT_ENTERPRISE_HAVING_DEPARTMENT = " SELECT E.id as enterpriseId, E.name, E.foundationDate, E.info, ET.id as typeId, ET.name as typeName FROM Enterprise as E LEFT JOIN EnterpriseType as ET on E.enterpriseTypeID = ET.id where E.id = (select EnterpriseID from Department where id = %d)";     
    private static final String QUERY_SELECT_ALL_DEPARTMENTS_WITH_TYPES_HAVING_EMPLOYEE = "SELECT D.id as departmentId, D.departmentName, DT.id as typeId, DT.name as typeName FROM Department as D LEFT JOIN DepartmentType as DT on D.departmentTypeID = DT.id where D.id in (select DepartmentID from employees_departments where EmployeeID = %d)"; 
    private static final String QUERY_SELECT_ALL_FROM_TABLE = " SELECT * FROM %s;";     

    private static final String QUERY_SELECT_ENTITY_WITH_TYPES_BY_ID = " SELECT id as enterpriseId * FROM %s where id = ?;";     
    private static final String QUERY_DELETE_UNEMPLOYED = "delete from employee where id not in (select employeeid from employees_departments);";
        
    private static final String QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS = "SELECT E.id, E.firstName, E.lastName, E.birthday, ET.id, ET.name, D.id, D.departmentName, DT.id, DT.name FROM Employee as E LEFT JOIN EmployeeType as ET on E.employeeTypeID = ET.id LEFT JOIN employees_departments as ED on E.id = ED.employeeID LEFT JOIN department as D on D.id = ED.DepartmentId LEFT JOIN DepartmentType as DT on D.DepartmentTypeID = DT.id;";
    private static final String QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS = 
            "SELECT E.id, E.foundationDate, E.info, E.name, ET.id, ET.name, D.id, D.departmentName, DT.id, DT.name FROM Enterprise as E"+
        "LEFT JOIN enterprisetype as ET"+ 
      "ON E.Enterprisetypeid = ET.id"+
        "LEFT JOIN department as D "+
      "ON E.id = D.Enterpriseid "+
             " LEFT JOIN departmentType as DT "+
      "ON D.departmenttypeid = DT.id ";
       

    protected static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("DAO");
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @PostConstruct
    public void init(){
        simpleJdbcInsert.withTableName(persistentClass.getSimpleName()).usingGeneratedKeyColumns("id");
    }

    /**
     * Persistent class that this dao works with
     */
    protected Class<T> persistentClass;

    SubDAO sub = new SubDAO();  
    /**
     * Constructor with fields
     *
     * @param persistentClass Class that this dao will work with
     */
    public GenericDAOImpl(Class<T> persistentClass) {

        this.persistentClass = persistentClass;

    }

    /**
     * <p>
     * This template method executes query with performing all needed
     * operations, like creating EntityManager, creating transaction,
     * committing, or rolling it back.
     * </p>
     * <p>
     * Methods sets parameters for the query as they appear in the parameters
     * list, by number starting from 1.
     * </p>
     * <p>
     * If
     * <code>singleResult = true</code> and no result is found, then
     * null is returned.
     * </p>
     * <p>
     * When multiple results are returned, they are being
     * dynamically casted to
     * REZ class. It should be able to cast to
     * {@link java.util.List}.
     * </p>
     *
     * @param <REZ> Class of the result
     * @param queryOrQueryName Query string or NamedQuery name
     * @param namedQuery Specifies, whether query is named query
     * @param singleResult Specifies, whether single result should be returned
     * @param initializeObjects If true, then function will iterate through all
     * object to initialize them
     * @param parameters Parameters. Specifing multiple parameters
     * separated by comma
     * @return Result of the query
     * 
     */

   
  /*  protected <REZ> REZ executeQuery(String query,
             boolean singleResult, Object... parameters) {

        if (query.isEmpty()) {
            throw new IllegalArgumentException(
                    "Query for executing cannot be null");
        }

        REZ result;
        List<?> list;


        connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
                
       
            list = hibernateTemplate.find(queryOrQueryName, parameters); //execute simple query
        

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
    }*/


    @Override
    public long getCount() {
       
        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL, Long.class);
  
    }

    @Override
    public void save(T entity) { 

        try {
            if (entity == null) {
                throw new IllegalArgumentException(
                        "Entity for saving cannot be null!");
                }

            
            if (persistentClass.equals(Enterprise.class)){
               
                sub.new EnterpriseSubDao().save((Enterprise) entity);}
            if (persistentClass.equals(EnterpriseType.class)){
            sub.new EnterpriseTypeSubDao().save((EnterpriseType) entity);}
              
            if (persistentClass.equals(Department.class)){
            sub.new DepartmentSubDao().save((Department) entity);}
            
            if (persistentClass.equals(DepartmentType.class)){
            sub.new DepartmentTypeSubDao().save((DepartmentType) entity);}
              
            if (persistentClass.equals(Employee.class)){
                sub.new EmployeeSubDao().save((Employee) entity);
                //sub.new EmployeeSubDao().updateManyToManyTable((Employee) entity);
            }
               
            if (persistentClass.equals(EmployeeType.class)){
            sub.new EmployeeTypeSubDao().save((EmployeeType) entity);}

            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(T entity) {

        try {
            if (entity == null) {
                throw new IllegalArgumentException(
                        "Entity for saving cannot be null!");
                }
     
            if (persistentClass.equals(Enterprise.class)){
                 sub.new EnterpriseSubDao().update((Enterprise) entity);}
            
            if (persistentClass.equals(EnterpriseType.class)){
            sub.new EnterpriseTypeSubDao().update((EnterpriseType) entity);}
              
            if (persistentClass.equals(Department.class)){
             sub.new DepartmentSubDao().update((Department) entity);}
            
            if (persistentClass.equals(DepartmentType.class)){
             sub.new DepartmentTypeSubDao().update((DepartmentType) entity);}
              
            if (persistentClass.equals(Employee.class)){
                 sub.new EmployeeSubDao().update((Employee) entity);
            } 
            if (persistentClass.equals(EmployeeType.class)){
             sub.new EmployeeTypeSubDao().update((EmployeeType) entity);}

            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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


    @Override
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
           if (persistentClass.equals(Enterprise.class)){
                 sub.new EnterpriseSubDao().delete((Enterprise) entity);}
            
            if (persistentClass.equals(EnterpriseType.class)){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.}
            }
              
            if (persistentClass.equals(Department.class)){
             sub.new DepartmentSubDao().delete((Department) entity);}
            
            if (persistentClass.equals(DepartmentType.class)){
                     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.}
            }
            if (persistentClass.equals(Employee.class)){
                 sub.new EmployeeSubDao().delete((Employee) entity);
            } 
            if (persistentClass.equals(EmployeeType.class)){
                     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


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

   @Override
    public List<T> findAll() {
       
        try {
            // find all entities
            if (persistentClass.equals(Enterprise.class)){
                return (List<T>) sub.new EnterpriseSubDao().findAll();}
            
            if (persistentClass.equals(EnterpriseType.class)){
                return (List<T>) sub.new EnterpriseTypeSubDao().findAll();
            }
              
            if (persistentClass.equals(Department.class)){
             return  (List<T>) sub.new DepartmentSubDao().findAll();}
            
            if (persistentClass.equals(DepartmentType.class)){
                return  (List<T>) sub.new DepartmentTypeSubDao().findAll();
            }     
            if (persistentClass.equals(Employee.class)){
                 return  (List<T>) sub.new EmployeeSubDao().findAll();
            } 
            if (persistentClass.equals(EmployeeType.class)){
                return  (List<T>) sub.new EmployeeTypeSubDao().findAll();
            }
            
        return new ArrayList<T>();
      
        }
         catch (Exception e) {
            return new ArrayList<T>();
         }
    }
    


    @Override
    public T findById(long id) {

        try {

            if (persistentClass.equals(Enterprise.class)){
                return  (T) sub.new EnterpriseSubDao().findById(id);}
            
            if (persistentClass.equals(EnterpriseType.class)){
                return  (T) sub.new EnterpriseTypeSubDao().findById(id);
            }
              
            if (persistentClass.equals(Department.class)){
             return  (T) sub.new DepartmentSubDao().findById(id);}
            
            if (persistentClass.equals(DepartmentType.class)){
                return  (T) sub.new DepartmentTypeSubDao().findById(id);
            }     
            if (persistentClass.equals(Employee.class)){
                 return  (T) sub.new EmployeeSubDao().findById(id);
            } 
            if (persistentClass.equals(EmployeeType.class)){
                return  (T) sub.new EmployeeTypeSubDao().findById(id);
            }
                return null;
                
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
/*    protected String addSortPropertyToQuery(String query, String propertySortBy,
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
    
    */
    /**
     * Receives connection and manipulates 
     */
    class SubDAO{
        class EnterpriseSubDao{
            void save(Enterprise enterprise){
                Map params = new LinkedHashMap();
                params.put("name", enterprise.getName());
                params.put("foundationDate", new java.sql.Date(enterprise.getFoundationDate().getYear(), enterprise.getFoundationDate().getMonth(), enterprise.getFoundationDate().getDay()));
                params.put("info", enterprise.getInfo());
                params.put("EnterpriseTypeID", enterprise.getType().getId());
                Number id = simpleJdbcInsert.executeAndReturnKey(params);
                enterprise.setId((Long) id);
            }
            void update(Enterprise enterprise){
                
                String query1 = "UPDATE Enterprise SET name=?, foundationDate=?, info=?, EnterpriseTypeID=? WHERE id=?;";
                String query2 = "UPDATE Department SET EnterpriseID=? WHERE id=?;";
                jdbcTemplate.update(query1, new Object[]{enterprise.getName(),
                    new java.sql.Date(enterprise.getFoundationDate().getYear(), enterprise.getFoundationDate().getMonth(), enterprise.getFoundationDate().getDay()),
                    enterprise.getInfo(),
                    enterprise.getType().getId(),
                    enterprise.getId()}
                );

                List<Object[]> list = new LinkedList<Object[]>();
                
                for(Department d : enterprise.getDepartments()){
                    list.add(new Object[]{ enterprise.getId(), d.getId() } );
                }
                
                jdbcTemplate.batchUpdate(query2, list);
                
            }
            void delete(Enterprise enterprise){
              
                
                Iterator<Department> it= null;
                if(!enterprise.getDepartments().isEmpty()){
                    
                    it = enterprise.getDepartments().iterator();
          
                
                while(it.hasNext()){
                    new DepartmentSubDao().delete(it.next());
                }
                }
             
                String query = String.format(QUERY_DELETE_ENTITY, persistentClass
                    .getSimpleName());
                
                jdbcTemplate.update(query, enterprise.getId());
            }
            List<Enterprise> findAll()  {
            
                return jdbcTemplate.query(QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS, new EnterpriseSetExtractor());
               
            }
            
            Enterprise findById(long id){
                Enterprise enterprise;
                
                List<Enterprise> l = jdbcTemplate.query(QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS+" where E.id = ?", new Object[]{id}, new EnterpriseSetExtractor());
                enterprise = l.get(0);
        
           
           return enterprise;
            }   
        }
        class EnterpriseTypeSubDao{
            void save(EnterpriseType type){
               
                Map params = new LinkedHashMap();
                params.put("name", type.getName());
                Number id = simpleJdbcInsert.executeAndReturnKey(params);
                type.setId((Long) id);

            }
            void update(EnterpriseType type) throws SQLException{
                
                String query = "UPDATE EnterpriseType SET name=? WHERE id=?;";
                
                jdbcTemplate.update(query, new Object[]{ type.getName(),
                                                         type.getId()
                });
                
            }
            List<EnterpriseType> findAll(){
                String query = String.format(QUERY_SELECT_ALL_FROM_TABLE, persistentClass.getSimpleName());
               
                return jdbcTemplate.query(query, new EnterpriseTypeRowMapper());
            }
            
            public EnterpriseType findById(long id) {

            String query = String.format(QUERY_SELECT_ALL_FROM_TABLE+" where id = ?", persistentClass.getSimpleName());
           
           return jdbcTemplate.queryForObject(query, new Object[]{id}, new EnterpriseTypeRowMapper());
            } 
            
        }
        class DepartmentSubDao{
            void save(Department department) throws SQLException{
               
                Map params = new LinkedHashMap();
                params.put("departmentName", department.getName());
                params.put("DepartmentTypeID", department.getType().getId());
                Number id = simpleJdbcInsert.executeAndReturnKey(params);
                department.setId((Long) id);
          
            }
            void update(Department department) throws SQLException{
               
                String query = "UPDATE Department SET departmentName=?, DepartmentTypeID=? WHERE id=?;";
                
                jdbcTemplate.update(query, new Object[]{ department.getName(),
                                                         department.getType().getId(),
                                                         department.getId()
                });
                
            }
 /////////////    TX       /////////////////////////////
            void delete(Department department){
                
                String query1 = "DELETE FROM employees_departments where DepartmentID = ?";
                String query2 = String.format(QUERY_DELETE_ENTITY, persistentClass
                    .getSimpleName());
                
                jdbcTemplate.update(query1, department.getId());
                jdbcTemplate.update(query2, department.getId());

                
                jdbcTemplate.update(QUERY_DELETE_UNEMPLOYED);
            }
            
            List<Department> findAll(){
                
                return jdbcTemplate.query(QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES, new DepartmentSetExtractor());

            }
            
            Department findById(long id) {
                
                Department department;
                
                List<Department> l = jdbcTemplate.query(QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES+" where D.id = ?", new Object[]{id}, new DepartmentSetExtractor());
                department = l.get(0);
        
           
           return department;
        
            } 
            
        }
        class DepartmentTypeSubDao{
            void save(DepartmentType type) throws SQLException{
             
                Map params = new LinkedHashMap();
                params.put("name", type.getName());
                Number id = simpleJdbcInsert.executeAndReturnKey(params);
                type.setId((Long) id);
                
            }
            void update(DepartmentType type) throws SQLException{
                
                String query = "UPDATE DepartmentType SET name=? WHERE id=?;";
                
                jdbcTemplate.update(query, new Object[]{ type.getName(),
                                                         type.getId()
                });

            }
            
            List<DepartmentType> findAll(){
                String query = String.format(QUERY_SELECT_ALL_FROM_TABLE, persistentClass.getSimpleName());
                return jdbcTemplate.query(query, new DepartmentTypeRowMapper());
            }
            
            DepartmentType findById(long id){
            String query = String.format(QUERY_SELECT_ALL_FROM_TABLE+" where id = ?", persistentClass.getSimpleName());
           
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new DepartmentTypeRowMapper());
            } 
        }
        class EmployeeSubDao{
            void save(Employee employee) throws SQLException{
                
                Map params = new LinkedHashMap();
                params.put("firstName", employee.getFirstName());
                params.put("lastName", employee.getLastName());
                params.put("birthday", new java.sql.Date(employee.getBirthday().getYear(), employee.getBirthday().getMonth(), employee.getBirthday().getDay()));
                params.put("EmployeeTypeID", employee.getType().getId());
                Number id = simpleJdbcInsert.executeAndReturnKey(params);
                employee.setId((Long) id);
                
                updateManyToManyTable(employee);
                
            }
            
            void updateManyToManyTable(Employee employee) throws SQLException{
     
                Iterator<T> it= null;
                if(!employee.getDepartments().isEmpty()){

                    it = (Iterator<T>) employee.getDepartments().iterator();
               
                }
                else{return;}
  
                while (it.hasNext()){
                   
                    String query = "INSERT into employees_departments values (?,?)";
                    try{
                    jdbcTemplate.update(query, employee.getId(), it.next().getId());
                    }
                   catch(DataAccessException e){
                       
                   }
          
                }
            }

             void update(Employee employee) throws SQLException{
                
                String query = "UPDATE Employee SET birthday=? firstName=? lastName=? EmployeeTypeID=? WHERE id=?;";
                
                jdbcTemplate.update(query, new Object[]{ new java.sql.Date(employee.getBirthday().getYear(), employee.getBirthday().getMonth(), employee.getBirthday().getDay()),
                                                         employee.getFirstName(),
                                                         employee.getLastName(),
                                                         employee.getType().getId(),
                                                         employee.getId()
                });
                
                updateManyToManyTable(employee);
               
            }
            
             ////// TX  ///////
            void delete(Employee employee){
                
                String query1 = "DELETE FROM employees_departments where EmployeeID = ?";
                String query2 = String.format(QUERY_DELETE_ENTITY, persistentClass
                    .getSimpleName());
                
                jdbcTemplate.update(query1, employee.getId());
                jdbcTemplate.update(query2, employee.getId());

            }
            
            List<Employee> findAll(){
                
                return jdbcTemplate.query(QUERY_SELECT_ALL_EMPLOYEES_WITH_DEPARTMENTS, new EmployeeSetExtractor());

            }
            
            public Employee findById(long id){
            Employee employee;
                
                List<Employee> l = jdbcTemplate.query(QUERY_SELECT_ALL_DEPARTMENTS_WITH_EMPLOYEES+" where E.id = ?", new Object[]{id}, new EmployeeSetExtractor());
                employee = l.get(0);
        
           
           return employee;
            }
            
        }
        
        class EmployeeTypeSubDao{
            void save(EmployeeType type) throws SQLException{
                
                Map params = new LinkedHashMap();
                params.put("name", type.getName());
                Number id = simpleJdbcInsert.executeAndReturnKey(params);
                type.setId((Long) id);
                
            }
            void update(EmployeeType type) throws SQLException{
                
                String query = "UPDATE EmployeeType SET name=? WHERE id=?;";
                
                jdbcTemplate.update(query, new Object[]{ type.getName(),
                                                         type.getId()
                });
                
            }
            List<EmployeeType> findAll(){
                String query = String.format(QUERY_SELECT_ALL_FROM_TABLE, persistentClass.getSimpleName());
                
                return jdbcTemplate.query(query, new EmployeeTypeRowMapper());
            }
            public EmployeeType findById(long id){
                
            String query = String.format(QUERY_SELECT_ALL_FROM_TABLE+" where id = ?", persistentClass.getSimpleName());  
          
           return jdbcTemplate.queryForObject(query, new Object[]{id}, new EmployeeTypeRowMapper());
            }
            
           
        }
      //  PersistentObjectCreator cr = new PersistentObjectCreator();
        
    }
    
  
    
private static final class EnterpriseSetExtractor
            implements ResultSetExtractor<List<Enterprise>> {

        @Override
        public List<Enterprise> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            
            Map<Long, Enterprise> map = new HashMap<Long, Enterprise>();
            Enterprise enterprise = null;
            
            while (rs.next()) {

                Long enterpriseId = rs.getLong(1);
                
                if (enterpriseId == null)
                    continue;
                
                enterprise = map.get(enterpriseId);
                
                if (enterprise == null) {
                    enterprise = new Enterprise();
                    enterprise.setId(enterpriseId);
                    enterprise.setName(rs.getString(4));
                    enterprise.setFoundationDate(rs.getDate(2));
                    enterprise.setInfo(rs.getString(3));           
                    enterprise.setType(new EnterpriseType(rs.getLong(5), rs.getString(6)));
   
                    map.put(enterpriseId, enterprise);

                }

                Long departmentId = rs.getLong(7);
                if (departmentId > 0) {
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString(8));
                    department.setType(new DepartmentType(rs.getLong(9), rs.getString(10)));
                    enterprise.setDepartments(department);
                }
            }
            return new ArrayList<Enterprise>(map.values());
        }
}

private static final class EnterpriseTypeRowMapper
            implements RowMapper<EnterpriseType> {

        @Override
        public EnterpriseType mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            long id = rs.getLong("id");
        String name = rs.getString("name");
        
        return new EnterpriseType(id, name);
            
        }
}

private static final class DepartmentSetExtractor
            implements ResultSetExtractor<List<Department>> {

        @Override
        public List<Department> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            
            Map<Long, Department> map = new HashMap<Long, Department>();
            Department department = null;
            
            while (rs.next()) {

                Long departmentId = rs.getLong(1);
                
                if (departmentId == null)
                    continue;
                
                department = map.get(departmentId);
                
                if (department == null) {
                    department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString(2));       
                    department.setType(new DepartmentType(rs.getLong(3), rs.getString(4)));
   
                    map.put(departmentId, department);

                }

                Long employeeId = rs.getLong(5);
                if (employeeId > 0) {
                    Employee employee = new Employee();
                    employee.setId(employeeId);
                    employee.setFirstName(rs.getString(6));
                    employee.setLastName(rs.getString(7));
                    employee.setBirthday(rs.getDate(8));
                    employee.setType(new EmployeeType(rs.getLong(9), rs.getString(10)));
                    department.setEmployees(employee);
                }
            }
            return new ArrayList<Department>(map.values());
        }
}

private static final class DepartmentTypeRowMapper
            implements RowMapper<DepartmentType> {

        @Override
        public DepartmentType mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            long id = rs.getLong("id");
        String name = rs.getString("name");
        
        return new DepartmentType(id, name);
            
        }
}

private static final class EmployeeSetExtractor
            implements ResultSetExtractor<List<Employee>> {

        @Override
        public List<Employee> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            
            Map<Long, Employee> map = new HashMap<Long, Employee>();
            Employee employee = null;
            
            while (rs.next()) {

                Long employeeId = rs.getLong(1);
                
                if (employeeId == null)
                    continue;
                
                employee = map.get(employeeId);
                
                if (employee == null) {
                    employee = new Employee();
                    employee.setId(employeeId);
                    employee.setFirstName(rs.getString(2));
                    employee.setLastName(rs.getString(3));
                    employee.setBirthday(rs.getDate(4));
                    employee.setType(new EmployeeType(rs.getLong(5), rs.getString(6)));
   
                    map.put(employeeId, employee);

                }

                Long departmentId = rs.getLong(7);
                if (employeeId > 0) {
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString(8));
                    department.setType(new DepartmentType(rs.getLong(9), rs.getString(10)));
                    employee.setDepartments(department);
                }
            }
            return new ArrayList<Employee>(map.values());
        }
}

private static final class EmployeeTypeRowMapper
            implements RowMapper<EmployeeType> {

        @Override
        public EmployeeType mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            long id = rs.getLong("id");
        String name = rs.getString("name");
        
        return new EmployeeType(id, name);
            
        }
}

      
   /* public Enterprise formEnterprise(ResultSet set) throws SQLException {
        Enterprise e = null;
        if (set.next()) {
            e = getEnterprise(set);
        }

        return e;
    }

    public List<Enterprise> formEnterprises(ResultSet set) throws SQLException {
        List<Enterprise> result = new ArrayList<Enterprise>();
        while (set.next()) {
            result.add(getEnterprise(set));
        }

        return result;
    }

    private Enterprise getEnterprise(ResultSet set) throws SQLException {
        Enterprise enterprise;
        long id = set.getLong(1);
        String name = set.getString(2);
        Date d = set.getDate(3);
        String info = set.getString(4);
        enterprise = new Enterprise(id, name, d, info);
        id = set.getLong(5);
        name = set.getString(6);
        enterprise.setType(new EnterpriseType(id, name));
        return enterprise;
    }
    
    public EnterpriseType formEnterpriseType(ResultSet set) throws SQLException {
        EnterpriseType type = null;
        if (set.next()) {
            type = getEnterpriseType(set);
        }

        return type;
    }

    public List<EnterpriseType> formEnterpriseTypes(ResultSet set) throws SQLException {
        List<EnterpriseType> result = new ArrayList<EnterpriseType>();
        while (set.next()) {
            result.add(getEnterpriseType(set));
        }

        return result;
    }

    private EnterpriseType getEnterpriseType(ResultSet set) throws SQLException {
        long id = set.getLong("id");
        String name = set.getString("name");
        
        return new EnterpriseType(id, name);
    }
    
    public Department formDepartment(ResultSet set) throws SQLException {
        Department department = null;
        if (set.next()) {
            department = getDepartment(set);
        }

        return department;
    }

    public List<Department> formDepartments(ResultSet set) throws SQLException {
        List<Department> result = new ArrayList<Department>();
        while (set.next()) {
            result.add(getDepartment(set));
        }

        return result;
    }

    private Department getDepartment(ResultSet set) throws SQLException {
        Department department;
        long id = set.getLong("departmentId");
        String name = set.getString("departmentName");
        department = new Department(id, name);
        id = set.getLong("typeId");
        name = set.getString("typeName");
        department.setType(new DepartmentType(id, name));
        return department;
    }
     public DepartmentType formDepartmentType(ResultSet set) throws SQLException {
        DepartmentType type = null;
        if (set.next()) {
            type = getDepartmentType(set);
        }

        return type;
    }

    public List<DepartmentType> formDepartmentTypes(ResultSet set) throws SQLException {
        List<DepartmentType> result = new ArrayList<DepartmentType>();
        while (set.next()) {
            result.add(getDepartmentType(set));
        }

        return result;
    }

    private DepartmentType getDepartmentType(ResultSet set) throws SQLException {
        long id = set.getLong("id");
        String name = set.getString("name");

      
        return new DepartmentType(id, name);
    }
        
      
            public Employee formEmployee(ResultSet set) throws SQLException {
        Employee type = null;
        if (set.next()) {
            type = getEmployee(set);
        }

        return type;
    }

    public List<Employee> formEmployees(ResultSet set) throws SQLException {
        List<Employee> result = new ArrayList<Employee>();
        while (set.next()) {
            result.add(getEmployee(set));
        }

        return result;
    }

    private Employee getEmployee(ResultSet set) throws SQLException {
        Employee employee;
        long id = set.getLong("employeeId");
        String firstName = set.getString("firstName");
        String lastName = set.getString("lastName");
        Date birthday = set.getDate("birthday");
        employee = new Employee(id, firstName, lastName, birthday);
        id = set.getLong("typeId");
        String name = set.getString("typeName");
        employee.setType(new EmployeeType(id, name));
        return employee;
    }
            
        
      
            public EmployeeType formEmployeeType(ResultSet set) throws SQLException {
        EmployeeType type = null;
        if (set.next()) {
            type = getEmployeeType(set);
        }

        return type;
    }

    public List<EmployeeType> formEmployeeTypes(ResultSet set) throws SQLException {
        List<EmployeeType> result = new ArrayList<EmployeeType>();
        while (set.next()) {
            result.add(getEmployeeType(set));
        }

        return result;
    }

    private EmployeeType getEmployeeType(ResultSet set) throws SQLException {
        long id = set.getLong("id");
        String name = set.getString("name");

      
        return new EmployeeType(id, name);
    }
        }*/
}
