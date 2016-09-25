package com.mycompany.hibernatemvn.DB.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitUtil {

    private ConnectionManager connectionManager;
    private final String CREATE_DATABASE ;
    private final String CREATE_TABLE;
    private final String DROP_DATABASE;
    private final String USE;


    public DBInitUtil(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS " + connectionManager.getDBName();
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS person (id SERIAL, name VARCHAR(255), age INT)";
        USE = "use " + connectionManager.getDBName();
        DROP_DATABASE = "DROP DATABASE IF EXISTS " + connectionManager.getDBName();
    }

    public void init(boolean drop){
        Connection connection = connectionManager.getConnection();
        try {
            Statement st = connection.createStatement();
            if(drop) st.executeUpdate(DROP_DATABASE);
            st.executeUpdate(CREATE_DATABASE);
            st.executeQuery(USE);
            st.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
