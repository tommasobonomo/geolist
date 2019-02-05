/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author giorgiosgl
 */
public class Database {
    public static final String NAME="GEODB";
    public static final String PASSWORD="GEODB";
    public static final String DB_URL = "jdbc:derby://localhost:1527/GEODB";  
    public static Connection connection = null;
    
    
    public static Connection openConnection() throws SQLException{
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");            
        } catch (ClassNotFoundException ex) {
            System.out.println("DRIVER NON TROVATO");
        }
        
        return DriverManager.getConnection(DB_URL, NAME, PASSWORD);
        
    }
    
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = openConnection();
        }
        return connection;
    }
    
    public static void closeConnection(Connection c) throws SQLException{    
        c.close();
    }
    
    
    
}
