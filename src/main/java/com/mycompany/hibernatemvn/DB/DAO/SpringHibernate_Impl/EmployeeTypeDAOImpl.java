/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl;

import com.mycompany.hibernatemvn.DB.DAO.EmployeeTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
@Repository()
public class EmployeeTypeDAOImpl extends GenericDAOImpl<EmployeeType> implements EmployeeTypeDAO {

    public EmployeeTypeDAOImpl(){
        super(EmployeeType.class);
    }
 
    @Override
    public List<EmployeeType> findByName(String name) {
        return hibernateTemplate.findByNamedQueryAndNamedParam("findEmployeeTypeByName", "name", name);

    }
}
