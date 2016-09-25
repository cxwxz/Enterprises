package com.mycompany.hibernatemvn.DB.Entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Class that holds information about department type
 *
 * @author Denis
 */
@Entity
@NamedQueries( {
		@NamedQuery(name = "findDepartmentTypeByName", query = "from DepartmentType where name = :name")
		 })
public class DepartmentType extends DomainObject{

    /**
     * Default serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Name of the department type
     */
    private String name;

    /**
     * Default empty constructor
     */
    public DepartmentType() {
    }

    public DepartmentType(Long id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * Constructor with arguments
     * 
     * @param name 
     */
    public DepartmentType(String name) {
        this.name = name;
    }

    /**
     * 
     * @return department type name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name of the department type
     */
    public void setName(String name) {
        this.name = name;
    }
}
