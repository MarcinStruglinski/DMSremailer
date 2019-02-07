package biz.ekspert.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;





public class DbConn {
	public Connection getConnection() {
        Properties properties = new Properties();
    	InputStream input = null;
        try {
        	
        	input = new FileInputStream("db.properties");

            properties.load(input);
        } catch (IOException e) {
            new RuntimeException("Cannot loaddb,propierties files");
        }
        try {
            return DriverManager.getConnection(properties.getProperty("URL"), properties);
        } catch (SQLException e) {
           e.printStackTrace();
            throw new RuntimeException("Canot conect to databes");
        }
    }
    




	public Connection getConnection_DD() {
        Properties properties = new Properties();
    	InputStream input = null;
        try {
        	
        	input = new FileInputStream("db.properties");

            properties.load(input);
        } catch (IOException e) {
            new RuntimeException("Cannot loaddb,propierties files");
        }
        try {
            return DriverManager.getConnection(properties.getProperty("URL_DD"), properties);
        } catch (SQLException e) {
           e.printStackTrace();
            throw new RuntimeException("Canot conect to databesdd");
        }
    }
    




}
