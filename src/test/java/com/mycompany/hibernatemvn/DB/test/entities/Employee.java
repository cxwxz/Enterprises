/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.test.entities;

import com.mycompany.hibernatemvn.DB.Entities.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.IndexColumn;

/**
 *
 * @author Denis
 */
@Entity
/*@NamedQueries( {
		@NamedQuery(name = "findEmployeeByFullName", query = "from Employee emp where emp.firstName = :firstName and emp.lastName = :LastName")
		 })*/
public class Employee extends DomainObject implements Serializable {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private long id;*/
    
    private static final long serialVersionUID = 1L;
    
    private String firstName;
    
    private String lastName;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    
    @ManyToOne
    @JoinColumn(name = "EmployeeTypeID")
    private EmployeeType type;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Employees_Departments",
            joinColumns =
            @JoinColumn(name = "EmployeeID"),
            inverseJoinColumns =
            @JoinColumn(name = "DepartmentID"))
    private List<Department> departments = new LinkedList<Department>();

    public Employee() {
    }

    public Employee(String firstName, String lastName, Date birthday,
            EmployeeType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.type = type;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Department department) {
        departments.add(department);
    }

    /*public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }*/

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }
}
