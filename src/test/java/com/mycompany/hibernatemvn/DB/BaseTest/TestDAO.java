/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.BaseTest;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Denis
 */
public class TestDAO {
   // HibernateUtils hibernateUtils;
    
  /* @Test
    public void init(){
         hibernateUtils = HibernateUtils.getHibernateUtils();
     }*/
    
    /*@BeforeClass
    public void init(){
        context = new ClassPathXmlApplicationContext("hibernate-cinfig.xml");
    }*/
    
  /* @Test
   public void testBean(){
       ApplicationContext  context;
       context = new ClassPathXmlApplicationContext("hibernateTest-config.xml");
       //EnterpriseDAO enterpriseDAO = context.getBean("enterpriseDAOImpl", EnterpriseDAO.class);
       //enterpriseDAO.save(new Enterprise("asdasd"));
       //assertNotNull(enterpriseDAO);
   }
   
   @Test
   public void test(){
       Assert.assertEquals(4+4, 8);
       //EnterpriseDAO enterpriseDAO = context.getBean("enterpriseDAOImpl", EnterpriseDAO.class);
       //enterpriseDAO.save(new Enterprise("asdasd"));
       //assertNotNull(enterpriseDAO);
   }
   */
}
