package com.mycompany.hibernatemvn.DB.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

/**
 *Class that holds information about employee
 * 
 * @author Denis
 */
@Entity
@NamedQueries( {
		@NamedQuery(name = "findEmployeeByFullName", query = "from Employee emp where emp.firstName = :firstName and emp.lastName = :LastName")
		 })
public class Employee extends DomainObject{

    /**
     * Default serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * First name of the employee
     */
    private String firstName;
    
    /**
     * Last name of the employee
     */
    private String lastName;
    
    /**
     * Birthday of the enployee
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    
    /*@Transient
    private Long enterpriseId = null;*/
    /**
     * Occupation of the employee
     */
    @ManyToOne
    @JoinColumn(name = "EmployeeTypeID")
    private EmployeeType type;
    
    /**
     * Departments in which the employee works
     */
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinTable(name = "Employees_Departments",
            joinColumns =
            @JoinColumn(name = "EmployeeID"),
            inverseJoinColumns =
            @JoinColumn(name = "DepartmentID"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"EmployeeID", "DepartmentID"})}
)
    /* @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Employees_Departments",
            joinColumns =
            @JoinColumn(name = "EmployeeID"),
            inverseJoinColumns =
            @JoinColumn(name = "DepartmentID"))*/
    private List<Department> departments = new LinkedList<Department>();

    /**
     * Default empty constructor
     */
    public Employee() {
    }

    /**
     * Constructor with arguments
     * 
     * @param firstName
     * @param lastName
     * @param birthday
     * @param type 
     */
    public Employee(String firstName, String lastName, Date birthday,
            EmployeeType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.type = type;

    }

    public Employee(Long id, String firstName, String lastName, Date birthday) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public Employee( Long id, String firstName, String lastName, Date birthday, EmployeeType type) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.type = type;
    }

    /**
     * 
     * @return first name of the employee
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return birthday 
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 
     * @param birthday 
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /*public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }*/

    /**
     * 
     * @return departments in which the employee works
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * 
     * @param departments list of departments in which the employee works
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

     /**
     * 
     * @param department in which the employee works
     */
    public void setDepartments(Department department) {
        departments.add(department);
    }

    /**
     * 
     * @return type of the employe
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * 
     * @param type of the employe
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }
}
