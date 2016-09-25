/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.BaseTest;

//import com.mycompany.hibernatemvn.DB.utils.;;


import com.mycompany.hibernatemvn.DB.utils.HibernateUtils;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.hibernate.Session;

/**
 *
 * @author Denis
 */
public class HibernateUtilsTesting {
      
   private HibernateUtils hibernateUtils;
    
    @BeforeClass
    public void init(){
         hibernateUtils = HibernateUtils.getHibernateUtils();
     }
     
    @Test//(dependsOnMethods = "testInit")
     public void testGetSession(){
        assertNotNull(hibernateUtils);
        Session session = hibernateUtils.getSession();
        assertNotNull(session);
     }
     
  /*   @Test(expectedExceptions = {HibernateException.class})//(dependsOnMethods = "testInit")
     public void testCloseSessionFactory(){
         hibernateUtils.close();
     }*/
     
  /*  @Test(dependsOnMethods = "testGetSession")
     public void test(){
         Enterprise enterprise = new Enterprise();
         enterprise.setName("asd");
         new EnterpriseDAOImpl(hibernateUtils).add(enterprise);
     }*/
  
     
}
