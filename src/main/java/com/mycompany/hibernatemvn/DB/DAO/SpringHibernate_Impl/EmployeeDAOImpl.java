/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl;

import com.mycompany.hibernatemvn.DB.DAO.EmployeeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
@Repository()
public class EmployeeDAOImpl extends GenericDAOImpl<Employee> implements EmployeeDAO {

    public EmployeeDAOImpl(){
        super(Employee.class);
    }
 
    @Override
    public List<Employee> findByFullName(String firstName, String lastName) {
        return hibernateTemplate.findByNamedQueryAndNamedParam("findEmployeeByFullName", new String[]{"firstName", "lastName"}, new Object[]{firstName, lastName});
    }

}
