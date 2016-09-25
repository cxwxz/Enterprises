/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl;

import com.mycompany.hibernatemvn.DB.DAO.DepartmentTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
@Repository()
public class DepartmentTypeDAOImpl extends GenericDAOImpl<DepartmentType> implements DepartmentTypeDAO {

    public DepartmentTypeDAOImpl(){
        super(DepartmentType.class);
    }
    
    @Override
    public List<DepartmentType> findByName(String name) {
        return hibernateTemplate.findByNamedQueryAndNamedParam("findDepartmentTypeByName", "name", name);

    }
}
