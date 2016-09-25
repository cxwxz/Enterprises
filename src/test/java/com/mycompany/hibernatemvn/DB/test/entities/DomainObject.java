package com.mycompany.hibernatemvn.DB.test.entities;

import com.mycompany.hibernatemvn.DB.Entities.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;





/**
 * General class for all entities, that makes it possible to store them in DB.
 * 
 * @author chs
 */
@MappedSuperclass
public abstract class DomainObject implements Serializable {
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
	
	/** Default locale to use */
/*	@Transient
	private Locale defLocale = new Locale("ru", "RU"); //$NON-NLS-1$ //$NON-NLS-2$
*/
	/**
	 * Simple plain constructor
	 */
	public DomainObject() {
		id = null;
	}

	/**
	 * Constructor that sets the id parameter
	 * 
	 * @param id
	 *            Id of the object to set
	 */
	public DomainObject(Long id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Constructor that initialized <code>this</code> object with values of the
	 * passed object.
	 * </p>
	 * <p>
	 * The method {@link #copyFieldsValuesFrom(DomainObject)} is used.
	 * </p>
	 * <p>
	 * Objects should be of the same class!
	 * </p>
	 * 
	 * @param obj
	 *            Another object to get values from
	 */
/*	public DomainObject(DomainObject obj) {
		id = null;
		copyFieldsValuesFrom(obj);
	}*/

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

    /**
     * @return the defLocale
     */
    /*	public Locale getDefLocale() {
    return defLocale;
    }
     */
    /**
     * @param defLocale the defLocale to set
     */
    /*	public void setDefLocale(Locale defLocale) {
    this.defLocale = defLocale;
    }
     */
    /**
     * <p>
     * Copying all properties of the passed <code>obj</code> object to
     * <code>this</code> object.
     * </p>
     * <p>
     * The property {@link #id} is not copied. Also all properties that extend
     * {@link Collection} and {@link Class} are not copied either.
     * </p>
     *
     * @param obj
     *            Object to get values from
     */
    /*@SuppressWarnings("unchecked")
    public void copyFieldsValuesFrom(DomainObject obj) {
    if ((obj != null) && (this.getClass().isAssignableFrom(obj.getClass()))) {
    try {
    Map<String, Object> props = PropertyUtils.describe(obj);
    for (Entry<String, Object> entry : props.entrySet()) {
    if (PropertyUtils.isWriteable(this, entry.getKey())) {
    Class<?> clazz = PropertyUtils.getPropertyType(obj,
    entry.getKey());
    if ((!Collection.class.isAssignableFrom(clazz))
    && (!clazz.isAssignableFrom(Class.class))
    && (!entry.getKey().equals("id"))) { //$NON-NLS-1$
    PropertyUtils.setProperty(this, entry.getKey(),
    entry.getValue());
    }
    }
    }
    } catch (Exception e) {
    Log log = LogFactory.getLog(getClass());
    log.error(String.format(
    Messages.getString("DomainObject.ErrorCopyFieldValues"), //$NON-NLS-1$
    this.toString(), obj.toString()), e);
    }
    }
    }
     */
    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

	

	/*
	 * (non-Javadoc)
	 * 
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
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    /*@Override
    public String toString() {
    return new ReflectionToStringBuilder(this,
    ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
     */
    @Override
    public String toString() {
        return "DomainObject{" + "id=" + id + '}';
    }
        
}
