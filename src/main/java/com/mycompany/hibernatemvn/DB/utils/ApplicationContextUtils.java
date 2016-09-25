/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.utils;

import java.util.Locale;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Denis
 */
public class ApplicationContextUtils {

    private static final Locale EN_LOCALE = new Locale("en");
    private static final Locale RU_LOCALE = new Locale("ru");
    private static Locale currentLocale = RU_LOCALE;
    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        if (context == null) {

           // context = new ClassPathXmlApplicationContext("spring-config.xml");
        }

        return context;
    }
    
    public static Locale getCurrentLocale(){
        
       return currentLocale;
    }
    
    public static void setCurrentLocale(String lang){
    
   
        if(lang=="en")
       currentLocale = EN_LOCALE;
        else if(lang=="ru")
       currentLocale = RU_LOCALE;
    }
}
