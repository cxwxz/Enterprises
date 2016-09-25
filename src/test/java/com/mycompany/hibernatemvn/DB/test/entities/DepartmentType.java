/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.test.entities;

import com.mycompany.hibernatemvn.DB.Entities.*;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Denis
 */
@Entity
/*@NamedQueries( {
		@NamedQuery(name = "findDepartmentTypeByName", query = "from DepartmentType where name = :name")
		 })*/
public class DepartmentType extends DomainObject implements Serializable {

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DepartmentTypeID")
    private long id;*/
    
    private static final long serialVersionUID = 1L;
    
    private String name;

    public DepartmentType() {
    }

    public DepartmentType(String name) {
        this.name = name;
    }

   /* public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
