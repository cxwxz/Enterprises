/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringHibernate_Impl;

import com.mycompany.hibernatemvn.DB.DAO.EnterpriseTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
@Repository()
public class EnterpriseTypeDAOImpl extends GenericDAOImpl<EnterpriseType> implements EnterpriseTypeDAO  {

   public EnterpriseTypeDAOImpl(){
        super(EnterpriseType.class);
    }
    
    @Override
    public List<EnterpriseType> findByName(String name) {
        return hibernateTemplate.findByNamedQueryAndNamedParam("findEnterpriseTypeByName", "name", name);

    }
}
