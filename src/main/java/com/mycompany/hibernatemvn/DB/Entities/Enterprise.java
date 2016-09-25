package com.mycompany.hibernatemvn.DB.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

/**
 * Class that holds information about enterprise
 *
 * @author Denis
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findEnterpriseByName", query = "from Enterprise where name = :name")
})
public class Enterprise extends DomainObject{

    /**
     * Default serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Name of the enterprise
     */
    private String name;
    
    /**
     * Information about the enterprise
     */  
    @Lob
    private String info;
    
    /**
     * Date of the enterprise foundation
     */ 
    @Temporal(TemporalType.DATE)
    private Date foundationDate;
    
    /**
     *
     */
    @Transient
    private Date created;
    @Transient
    private Date updated;

    /**
     * Date of the object persisting
     */
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    /**
     * Date of the object updating
     */
    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
    
    // @Formula(" ( new Date().getTime() - foundationDate.getTime() ) / (  24 * 60 * 60 * 1000  ) ) / 366")
    /* @PrePersist
     public void prePersist()  {
     years = ( ( new Date().getTime() - foundationDate.getTime() ) / (  24 * 60 * 60 * 1000  ) ) / 366;
     }*/
    
    /**
     * Type of the enterprise
     */
    @ManyToOne()
    @JoinColumn(name = "EnterpriseTypeID")
    private EnterpriseType type;

    /**
     * Departments of the enterprise
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "enterprise")
    //@JoinColumn(name = "EnterpriseID")
    private List<Department> departments = new LinkedList<Department>();

    /**
     * Default empty constructor
     */
    public Enterprise() {
    }

    /**
     * Constructor with arguments
     *
     * @param name name
     * @param foundationDate day of foundation
     * @param type type of the enterprise
     */
    public Enterprise(String name, Date foundationDate,
            EnterpriseType type) {
        this.name = name;
        this.foundationDate = foundationDate;
        this.type = type;
    }

    public Enterprise(Long id, String name, Date foundationDate, String info) {
        super(id);
        this.name = name;
        this.info = info;
        this.foundationDate = foundationDate;
    }

    /**
     *
     * @return name
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

    /**
     *
     * @return foundationDate
     */
    public Date getFoundationDate() {
        return foundationDate;
    }

    /**
     *
     * @param foundationDate
     */
    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    /**
     *
     * @return type
     */
    public EnterpriseType getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(EnterpriseType type) {
        this.type = type;
    }

    /**
     *
     * @return list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     *
     * @param department
     */
    public void setDepartments(Department department) {
        departments.add(department);
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     *
     * @return information
     */
    public String getInfo() {
        return info;
    }

    /**
     *
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     *
     * @return persisting date of the object
     */
    public Date getCreated() {
        return created;
    }

    /**
     *
     * @return updated date of the object
     */
    public Date getUpdated() {
        return updated;
    }

    /*@Override
    public String toString() {
        return "Enterprise{" +"id=" + id + ", name=" + name + ", info=" + info + ", foundationDate=" + foundationDate + ", created=" + created + ", updated=" + updated + ", type=" + type + ", departments=" + departments + '}';
    }*/
    
    
}
