/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.test.entities;

import com.mycompany.hibernatemvn.DB.Entities.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Formula;

/**
 *
 * @author Denis
 */
@Entity
/*@NamedQueries( {
		@NamedQuery(name = "findEnterpriseByName", query = "from Enterprise where name = :name")
		 })*/
public class Enterprise extends DomainObject implements Serializable{

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EnterpriseID")
    private long id;*/
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    
    @Lob
    private String info;
    
    @Temporal(TemporalType.DATE)
    private Date foundationDate;
    
    @Transient
    private Date created;
    @Transient
    private Date updated;

     @PrePersist
     protected void onCreate() {
     created = new Date();
     }

      @PreUpdate
     protected void onUpdate() {
    updated = new Date();
    }
    
   // @Formula(" ( new Date().getTime() - foundationDate.getTime() ) / (  24 * 60 * 60 * 1000  ) ) / 366")
 
    
   /* @PrePersist
    public void prePersist()  {
    years = ( ( new Date().getTime() - foundationDate.getTime() ) / (  24 * 60 * 60 * 1000  ) ) / 366;
  }*/
    
    @ManyToOne(/*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "EnterpriseTypeID")
    private EnterpriseType type;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "EnterpriseID") 
    private List<Department> departments;  

    public Enterprise() {
    }

    public Enterprise(String name, Date foundationDate,
            EnterpriseType type) {
        this.name = name;
        this.foundationDate = foundationDate;
        this.type = type;
 

    }
    
    public Enterprise(String name) {
        this.name = name;
        this.foundationDate = foundationDate;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    /*public long getId() {
        return id;
    }

    public void setId(long id) {
        his.id = id;
    }*/

    public EnterpriseType getType() {
        return type;
    }

    public void setType(EnterpriseType type) {
        this.type = type;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Department department) {
        departments.add(department);
    }

    public void setId(int hashCode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }


    
     
}
