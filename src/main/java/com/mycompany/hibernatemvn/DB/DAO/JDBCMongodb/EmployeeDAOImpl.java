/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCMongodb;

import com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl.*;
import com.mycompany.hibernatemvn.DB.DAO.EmployeeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
//@Repository("employeeDAO")
public class EmployeeDAOImpl extends GenericDAOImpl<Employee> implements EmployeeDAO {

    @Override
    public List<Employee> findByFullName(String firstName, String lastName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    
}
