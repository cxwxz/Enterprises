/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.services.hibernateSpring;

import com.mycompany.hibernatemvn.DB.DAO.EnterpriseTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import com.mycompany.hibernatemvn.services.EnterpriseTypeService;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service("enterpriseTypeService") 
@Repository 
@Transactional
public class EnterpriseTypeServiceImpl implements EnterpriseTypeService {

}
