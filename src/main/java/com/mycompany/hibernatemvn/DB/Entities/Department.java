package com.mycompany.hibernatemvn.DB.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *Class that holds information about department
 * 
 * @author Denis
 */
@Entity()
@NamedQueries( {
		@NamedQuery(name = "findDepartmentByName", query = "from Department where name = ? and enterpriseId = ?")
		 })
public class Department extends DomainObject{

    /**
     * Default serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Name of the department
     */
    @Column(name = "departmentName")
    private String name;
    
    /**
     * Type of the department
     */
    @ManyToOne
    @JoinColumn(name = "DepartmentTypeID")
    private DepartmentType type;
    
    @Transient
    private Long enterpriseId = null;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EnterpriseID")
    private Enterprise enterprise;
    
    /*@ManyToOne
    private Enterprise enterprise;*/
    
    /**
     * Employees which the department has
     */
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "departments", fetch = FetchType.EAGER )
    //@ManyToMany(mappedBy = "departments", fetch = FetchType.EAGER )
    @Fetch(value = FetchMode.SUBSELECT)
   // private Set<Employee> employees=new HashSet<Employee>();
    private List<Employee> employees=new LinkedList<Employee>();
    /**
     * Default empty constructor
     */
    public Department() {
    
    }

    /**
     * Constructor with arguments
     * 
     * @param name
     * @param type 
     */
    public Department(String name, DepartmentType type) {
        this.name = name;
        this.type = type;
        
    }

    public Department(Long id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * 
     * @return name of the department
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name of the department
     */
    public void setName(String name) {
        this.name = name;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    
    /**
     * 
     * @return employees of the department
     */
   /* public Set<Employee> getEmployees() {
        return employees;
    }*/
    
    /**
     * 
     * @param employees of the department
     */
   /* public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }*/

    /**
     * 
     * @param employee of the department
     */
    public void setEmployees(Employee employee) {
        employees.add(employee);
    }

    /**
     * 
     * @return type of the department
     */
    public DepartmentType getType() {
        return type;
    }

    /**
     * 
     * @param type of the department
     */
    public void setType(DepartmentType type) {
        this.type = type;
    }

    public Enterprise getEnterprise() {
    return enterprise;
    }
    public void setEnterprise(Enterprise enterprise) {
    this.enterprise = enterprise;
    }
    @Override
    public String toString() {
        return "Department{" + "name=" + name + ", type=" + type + '}';
    }
    
    
    
}
