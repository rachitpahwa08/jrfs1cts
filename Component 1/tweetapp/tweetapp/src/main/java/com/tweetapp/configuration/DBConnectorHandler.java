package com.tweetapp.configuration;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnectorHandler {
    public static Connection getConnection(){
        Properties props = new Properties();
        Connection connection = null;
        try {
            InputStream inputStream=DBConnectorHandler.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(inputStream);
            connection = DriverManager.getConnection(props.getProperty("connection-url"),props.getProperty("user"),props.getProperty("password"));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
