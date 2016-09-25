package com.mycompany.hibernatemvn.DB.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Class that holds information about enterprise type
 *
 * @author Denis
 */
@Entity
@NamedQueries( {
		@NamedQuery(name = "findEnterpriseTypeByName", query = "from EnterpriseType where name = :name")
		 })
public class EnterpriseType extends DomainObject{

    /**
     * Default serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Name of the enterprise type
     */
    private String name;
 
    /**
     * Default empty constructor
     */
    public EnterpriseType() {
    }

    /**
     * Constructor with arguments
     * 
     * @param name 
     */
    public EnterpriseType(String name) {
        this.name = name;
    }

    public EnterpriseType(Long id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * 
     * @return the type name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
}
