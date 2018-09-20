/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.DB;

import it.unitn.aa1718.webprogramming.geolists.DB.DBConnection;
import java.sql.SQLException;

/**
 *
 * @author Lorenzo
 */

public class Derby {
    
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        DBConnection Connection = new DBConnection();
        DBInsert Insert = new DBInsert();   
    }
}
