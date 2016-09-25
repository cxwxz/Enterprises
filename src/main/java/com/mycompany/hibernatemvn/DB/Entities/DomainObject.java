package com.mycompany.hibernatemvn.DB.Entities;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * General class for all entities, that makes it possible to store them in DB.
 *
 * @author Denis
 */
@MappedSuperclass
public abstract class DomainObject implements Serializable  {

    /**
     * Default serial version id
     */
    private static final long serialVersionUID = 1L;
    /**
     * Unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    public DomainObject() {
    }

    /**
     * Constructor that sets the id parameter
     *
     * @param id Id for the object to set
     */
    public DomainObject(Long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DomainObject other = (DomainObject) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DomainObject{" + "id=" + id + '}';
    }
}
