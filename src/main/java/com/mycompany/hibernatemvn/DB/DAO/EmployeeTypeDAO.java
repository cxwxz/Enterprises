/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO;

import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import java.util.List;

/**
 * Employeetype DAO interface.
 * @author Denis
 */
public interface EmployeeTypeDAO extends GenericDAO<EmployeeType> {

    public List<EmployeeType> findByName(String name);

}
