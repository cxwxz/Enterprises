/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl;

import com.mycompany.hibernatemvn.DB.DAO.EnterpriseDAO;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
@Repository()
public class EnterpriseDAOImpl extends GenericDAOImpl<Enterprise> implements EnterpriseDAO {
 
    public EnterpriseDAOImpl(){
        super(Enterprise.class);
    }
    
    @Override
    public List<Enterprise> findByName(String name) {
        return hibernateTemplate.findByNamedQueryAndNamedParam("findEnterpriseByName", "name", name);
    }

}
