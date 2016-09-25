/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.services;

import com.mycompany.hibernatemvn.DB.DAO.*;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Denis
 */
public interface EnterpriseService {
void saveEnterprise(String name, String date, String type);
Enterprise findById(long id);
public List<Enterprise> findAll();
void saveDepartment(String name, String type, /*long enterpriseId*/Enterprise enterprise);
void updateEnterprise(Enterprise enteprise);
void deleteEnterprise(Enterprise enteprise);
void updateEnterprise(String name, String date, String type, Enterprise enterprise);
}
