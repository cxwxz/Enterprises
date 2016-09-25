/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO;

import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
import java.util.List;

/**
 * Enterprisertpe DAO interface.
 * @author Denis
 */
public interface EnterpriseTypeDAO extends GenericDAO<EnterpriseType>   {

    public List<EnterpriseType> findByName(String name) ;

}
