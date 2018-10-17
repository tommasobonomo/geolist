/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**Data access object for the table user in the database
 *
 * @author giorgiosgl
 */
public class UserDAO {
    
    private User createUser(ResultSet rs) throws SQLException{
        return new User(rs.getString("username"), rs.getString("name"), rs.getString("lastname")
                , rs.getString("email"), rs.getString("password"), rs.getBoolean("admin"));
    }
    
    /** Return a list of all user in the database
     *
     * @return List of User
     * @throws SQLException
     */
    public List<User> getAllUsers() throws SQLException{
        String query= "SELECT * FROM Users";
        List<User> resList = new ArrayList<>();
        
        Connection c = Database.openConnection();
        
        Statement s = c.createStatement();
        ResultSet rs=s.executeQuery(query);
        
        while(rs.next()){
           resList.add(createUser(rs));
        }
        
        rs.close();
        s.close();
        Database.closeConnection(c);
        
        return resList;
    }
    
}
