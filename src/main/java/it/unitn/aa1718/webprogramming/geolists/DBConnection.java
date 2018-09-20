/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Lorenzo
 */

public class DBConnection {
    private static final String DRIVER ="org.apache.derby.jdbc.EmbeddedDriver"; 
    private static final String DB_URL = "jdbc:derby://localhost:1527/Supermarket";  // link al database in localhost    
    Connection Connect;
    

    
    
    public DBConnection() throws SQLException{

        try {
            this.Connect = DriverManager.getConnection(DB_URL+"create=true", "DoppioDiPig", "DoppioDiPig"); // link al database, username e passworld
            if(this.Connect != null){
                System.out.println("Connesso al database");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
