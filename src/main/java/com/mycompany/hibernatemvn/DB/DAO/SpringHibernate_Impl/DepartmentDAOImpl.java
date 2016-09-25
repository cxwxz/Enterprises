/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl;

import com.mycompany.hibernatemvn.DB.DAO.DepartmentDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
@Repository()
public class DepartmentDAOImpl extends GenericDAOImpl<Department> implements DepartmentDAO {

    public DepartmentDAOImpl(){
        super(Department.class);
    }

    @Override
    public List<Department> findByName(String name, long enterpriseId) {
        return hibernateTemplate.findByNamedQueryAndNamedParam( "findDepartmentByName", new String[]{"name","enterpriseId"}, new Object[]{name, enterpriseId} );
    }

}
