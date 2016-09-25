/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO;

import com.mycompany.hibernatemvn.DB.Entities.Department;
import java.util.List;

/**
 * Department DAO interface.
 * @author Denis
 */
public interface DepartmentDAO extends GenericDAO<Department> {

    public List<Department> findByName(String name, long enterpriseId);

}
