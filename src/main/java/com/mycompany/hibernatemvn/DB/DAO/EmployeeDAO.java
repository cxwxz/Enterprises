/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO;

import com.mycompany.hibernatemvn.DB.Entities.Employee;
import java.util.List;

/**
 * Employee DAO interface. 
 * @author Denis
 */
public interface EmployeeDAO extends GenericDAO<Employee> {
    
    public List<Employee> findByFullName(String firstName, String lastName);
}

