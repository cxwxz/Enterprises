/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO;

import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import java.util.List;

/**
 * Departmenttype DAO interface.
 * @author Denis
 */
public interface DepartmentTypeDAO extends GenericDAO<DepartmentType> {

    public List<DepartmentType> findByName(String name);

}
