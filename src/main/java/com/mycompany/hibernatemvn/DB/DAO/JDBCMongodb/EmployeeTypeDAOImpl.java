/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCMongodb;

import com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl.*;
import com.mycompany.hibernatemvn.DB.DAO.EmployeeTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
//@Repository("employeeTypeDAO")
public class EmployeeTypeDAOImpl extends GenericDAOImpl<EmployeeType> implements EmployeeTypeDAO {

    @Override
    public List<EmployeeType> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
   
}
