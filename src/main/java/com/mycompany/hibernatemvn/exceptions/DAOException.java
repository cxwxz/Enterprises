/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.exceptions;

import com.mycompany.hibernatemvn.DB.DAO.GenericDAO;

/**
 *
 * @author Denis
 */
public class DAOException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

	/** Service that caused exception */
	protected GenericDAO<?> dao;

	public DAOException() {
		super();
	}

	/**
	 * @param message
	 *            Message with exception
	 * @param cause
	 *            Cause of exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 *            Message with exception
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 *            Cause of exception
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param service
	 *            {@link IGenericService} object
	 */
	public DAOException(GenericDAO<?> dao) {
		super();
		this.dao = dao;
	}

	/**
	 * @param message
	 *            Message with exception
	 * @param cause
	 *            Cause of exception
	 * @param service
	 *            {@link IGenericService} object
	 */
	public DAOException(String message, Throwable cause,
			GenericDAO<?> dao) {
		super(message, cause);
		this.dao = dao;
	}

	/**
	 * @param message
	 *            Message with exception
	 * @param service
	 *            {@link IGenericService} object
	 */
	public DAOException(String message, GenericDAO<?> dao) {
		super(message);
		this.dao = dao;
	}

	/**
	 * @param cause
	 *            Cause of exception
	 * @param service
	 *            {@link IGenericService} object
	 */
	public DAOException(Throwable cause,
			GenericDAO<?> dao) {
		super(cause);
		this.dao = dao;
	}


	public GenericDAO<?> getDAO() {
		return dao;
	}
    
}
