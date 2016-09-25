/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCMongodb;

import com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl.*;
import com.mycompany.hibernatemvn.DB.DAO.DepartmentDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
//@Repository("departmentDAO")
public class DepartmentDAOImpl extends GenericDAOImpl<Department> implements DepartmentDAO {

    /*@Override
    public List<Department> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public List<Department> findByName(String name, long enterpriseId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
