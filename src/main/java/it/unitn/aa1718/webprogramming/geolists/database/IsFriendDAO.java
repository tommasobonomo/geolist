/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.IsFriend;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giorgiosgl
 */
public class IsFriendDAO{

    
    
    /**
     * get user who have isFriend to that list
     * @param listID
     * @return list of user
     */
    public List<User> getFriends(long userID) {
        String query1 = "SELECT usr2 FROM IsFriend AS F WHERE F.usr1 = " + userID;
        String query2 = "SELECT usr1 FROM IsFriend AS F WHERE F.usr2 = " + userID;
        
        List<User> list = new ArrayList<>();
        UserDAO a = new UserDAO();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query1); //usr2
            
            while (rs.next()) {
                list.add(a.get(rs.getLong("usr2")).get());
            }
            
            rs = s.executeQuery(query2); //usr1
            
            while (rs.next()) {
                list.add(a.get(rs.getLong("usr1")).get());
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }

    /**
     * add isfreind on db
     * @param obj
     */
    public void create(IsFriend obj) {
        String query= "INSERT INTO GEODB.ISFRIEND(USR1,USR2)\n" +
                        "VALUES (?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getUserID1());
            ps.setLong(2, obj.getUserID2());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }

    /**
     * delete the object a
     * @param a
     */
    public void delete(IsFriend obj) {
        String query ="DELETE FROM IsFriend WHERE usr1=? and usr2=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getUserID1());
            ps.setLong(2, obj.getUserID2());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
    }
}
