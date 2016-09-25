/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO;

import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import java.util.List;

/**
 * Enterprise DAO interface.
 * 
 * @author Denis
 */
public interface EnterpriseDAO extends GenericDAO<Enterprise> {

    public List<Enterprise> findByName(String name);

}
