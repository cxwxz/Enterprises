/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.test.entities;

import com.mycompany.hibernatemvn.DB.Entities.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.IndexColumn;

/**
 *
 * @author Denis
 */
@Entity()
/*@NamedQueries( {
		@NamedQuery(name = "findDepartmentByName", query = "from Department where name = :name")
		 })*/
public class Department extends DomainObject implements Serializable {

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DepartmentID")
    private long id;*/
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "departmentName")
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "DepartmentTypeID")
    private DepartmentType type;
    
    @ManyToMany(mappedBy = "departments", fetch = FetchType.EAGER )
    private Set<Employee> employees=new HashSet<Employee>();
    /*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EnterpriseID")
    private Enterprise enterprise;*/

    public Department() {
    }

    public Department(String name, DepartmentType type) {
        this.name = name;
        this.type = type;
        this.employees = new HashSet<Employee>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Employee employee) {
        employees.add(employee);
    }

   /* public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
*/
    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

   /* public Enterprise getenterprise() {
        return enterprise;
    }

    public void setenterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }*/
}
