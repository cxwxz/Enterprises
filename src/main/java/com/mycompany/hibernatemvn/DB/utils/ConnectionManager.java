package com.mycompany.hibernatemvn.DB.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which provide JDBC connection utils with database.
 *
 * @author Denis
 */
public class ConnectionManager {

    private Connection connection;
    private String url;// = "jdbc:mysql://localhost:3306/hibernate";
    private String login;// = "root";
    private String pass;// = "1";
    private String DBName;// = "hibernate";
    Properties prop = new Properties();
    InputStream input = null;

    {
        try {

            input = new FileInputStream("db.connection.properties");
            prop.load(input);
            
            url = prop.getProperty("connectionURL");
            login = prop.getProperty("connectionUserName");
            pass = prop.getProperty("connectionPassword");
            DBName = prop.getProperty("connectionDBName");

        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    /**
     * Constructor which loads JDBC driver
     */
    public ConnectionManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getDBName() {
        return DBName;
    }

    public Connection reOpenConnection() {
        this.closeConnection();
        return this.getConnection();
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, login, pass);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return connection;

    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
