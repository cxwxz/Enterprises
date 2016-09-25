package com.mycompany.hibernatemvn.DB.Entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *Class that holds information about employee type
 * 
 * @author Denis
 */
@Entity
@NamedQueries( {
		@NamedQuery(name = "findEmployeeTypeByName", query = "from EmployeeType where name = :name")
		 })
public class EmployeeType extends DomainObject{

    /**
     * Default serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Name of the employee type
     */
    private String name;

    /**
     * Default empty constructor
     */
    public EmployeeType() {
    }

    /**
     * Constructor with arguments
     * 
     * @param name 
     */
    public EmployeeType(String name) {
        this.name = name;
    }

    public EmployeeType(Long id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * 
     * @return name type
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name type
     */
    public void setName(String name) {
        this.name = name;
    }
}
